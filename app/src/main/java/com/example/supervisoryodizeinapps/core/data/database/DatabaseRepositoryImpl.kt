package com.example.supervisoryodizeinapps.core.data.database

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.example.supervisoryodizeinapps.core.data.ResourceState
import com.example.supervisoryodizeinapps.core.domain.enum.AttendanceType
import com.example.supervisoryodizeinapps.core.domain.model.AttendanceHistoryModel
import com.example.supervisoryodizeinapps.core.domain.model.attendance.Attendance
import com.example.supervisoryodizeinapps.core.domain.model.attendanceMonitor.AttendanceMonitorCellModel
import com.example.supervisoryodizeinapps.core.domain.model.auth.User
import com.example.supervisoryodizeinapps.core.domain.model.division.Division
import com.example.supervisoryodizeinapps.core.domain.model.division.DivisionResponse
import com.example.supervisoryodizeinapps.core.domain.model.monitor.TargetModel
import com.example.supervisoryodizeinapps.core.domain.model.monitor.TargetModelCell
import com.example.supervisoryodizeinapps.core.domain.model.monitor.TargetModelRequest
import com.example.supervisoryodizeinapps.core.extension.toString
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneOffset.*
import java.time.temporal.ChronoUnit
import java.util.Date
import javax.inject.Inject

open class DatabaseRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val db: FirebaseFirestore,
) : DatabaseRepository {
    @SuppressLint("SuspiciousIndentation")
    override fun createUser(user: User): Flow<ResourceState<Boolean>> {
        val dbUsers: DocumentReference = db.collection("Users").document(user.uid)
        return flow {
            emit(value = ResourceState.LOADING())
            user.idEmployee = Timestamp.now().nanoseconds.toString()
            dbUsers.set(user).await()
            emit(value = ResourceState.SUCCESS(data = true))
        }.catch {
            emit(value = ResourceState.ERROR(it.message.toString()))
        }
    }

    @SuppressLint("SuspiciousIndentation")
    override fun doAttendance(attendance: Attendance): Flow<ResourceState<Boolean>> {
        attendance.uid = firebaseAuth.uid ?: "-"
        val dbAttendances = db.collection("Attendances")
        return flow {
            emit(value = ResourceState.LOADING())
            dbAttendances.add(attendance).await()
            emit(value = ResourceState.SUCCESS(data = true))
        }.catch {
            emit(value = ResourceState.ERROR(it.message.toString()))
        }
    }

    @SuppressLint("SuspiciousIndentation")
    override fun getCurrentUser(): Flow<ResourceState<User>> {
        val dbUsers: DocumentReference = db.collection("Users").document(firebaseAuth.uid ?: "")
        return flow {
            emit(value = ResourceState.LOADING())
            val user = dbUsers.get().await()

            if (user != null) {
                emit(value = ResourceState.SUCCESS(data = user.toObject(User::class.java)!!))
            } else {
                emit(value = ResourceState.ERROR("No such document"))
            }
        }.catch {
            emit(value = ResourceState.ERROR(it.message.toString()))
        }
    }

    @SuppressLint("SuspiciousIndentation")
    override fun updateCurrentUser(user: User): Flow<ResourceState<Boolean>> {
        val dbUsers: DocumentReference = db.collection("Users").document(user.uid)
        return flow {
            emit(value = ResourceState.LOADING())
            dbUsers.set(user).await()
            emit(value = ResourceState.SUCCESS(data = true))
        }.catch {
            emit(value = ResourceState.ERROR(it.message.toString()))
        }
    }

    @SuppressLint("SuspiciousIndentation")
    override fun getDivisions(): Flow<ResourceState<List<Division>>> {
        val dubDivision = db.collection("Divisions")
        return flow {
            emit(value = ResourceState.LOADING())
            val divisions = dubDivision.get().await()

            val result: MutableList<Division> = mutableListOf()

            for (document in divisions) {
                val data = document.data
                val id = document.id
                result.add(Division(id, data["name"] as String, listOf()))
            }
            emit(value = ResourceState.SUCCESS(data = result.toList()))
        }.catch {
            emit(value = ResourceState.ERROR(it.localizedMessage.toString()))
        }
    }

    @SuppressLint("SuspiciousIndentation")
    override fun getTargets(division: String): Flow<ResourceState<List<TargetModelCell>>> {
        val dbTargets = db.collection("Targets")
            .whereEqualTo("idDivision", division)
        val dbUsers = db.collection("Users")
        return flow {
            emit(value = ResourceState.LOADING())

            try {
                val usersRaw = dbUsers.get().await()
                val users: MutableList<User> = mutableListOf()
                for (document in usersRaw) {
                    users.add(document.toObject(User::class.java))
                }

                val targetsRaw = dbTargets.get().await()

                if (!targetsRaw.isEmpty) { // Check if there are any documents
                    val result: MutableList<TargetModelCell> = mutableListOf()

                    for (document in targetsRaw) {
                        val data = document.data
                        val id = document.id
                        val name =
                            users.firstOrNull { user -> user.idEmployee == data["idEmployee"] as String }
                        result.add(
                            TargetModelCell(
                                name = name?.name ?: "",
                                target = TargetModel(
                                    id = id,
                                    idEmployee = data["idEmployee"] as String,
                                    idDivision = data["idDivision"] as String,
                                    dateStart = data["dateStart"] as String,
                                    dateFinish = data["dateFinish"] as String,
                                    targetBeenDone = (data["targetBeenDone"] as Long).toInt(),
                                    totalTarget = (data["totalTarget"] as Long).toInt(),
                                    productType = data["productType"] as String,
                                    targetType = data["targetType"] as String,
                                )
                            )
                        )
                    }

                    Log.d("TARGET", result.toList().toString())
                    emit(value = ResourceState.SUCCESS(data = result.toList()))
                } else {
                    emit(value = ResourceState.ERROR("No documents found"))
                }
            } catch (e: Exception) {

                Log.d("TARGET", "${e.localizedMessage} + ${e.message}")
                emit(value = ResourceState.ERROR(e.localizedMessage ?: "Unknown error"))
            }
        }
    }

    @SuppressLint("SuspiciousIndentation")
    override fun createTargets(
        division: String,
        target: TargetModelRequest
    ): Flow<ResourceState<Boolean>> {
        val dbTargets = db.collection("Targets")
        return flow {
            emit(value = ResourceState.LOADING())
            dbTargets.add(target).await()
            emit(value = ResourceState.SUCCESS(data = true))
        }.catch {
            emit(value = ResourceState.ERROR(it.localizedMessage.toString()))
        }
    }

    @SuppressLint("SuspiciousIndentation")
    override fun updateTarget(
        target: TargetModelRequest,
        idTarget: String
    ): Flow<ResourceState<Boolean>> {
        val dbTargets = db.collection("Targets").document(idTarget)
        return flow {
            emit(value = ResourceState.LOADING())
            dbTargets.set(target).await()
            emit(value = ResourceState.SUCCESS(data = true))
        }.catch {
            emit(value = ResourceState.ERROR(it.localizedMessage.toString()))
        }
    }

    @SuppressLint("SuspiciousIndentation")
    override fun deleteTarget(
        target: TargetModelRequest,
        idTarget: String
    ): Flow<ResourceState<Boolean>> {
        val dbTargets = db.collection("Targets").document(idTarget)
        return flow {
            emit(value = ResourceState.LOADING())
            dbTargets.delete().await()
            emit(value = ResourceState.SUCCESS(data = true))
        }.catch {
            emit(value = ResourceState.ERROR(it.localizedMessage.toString()))
        }
    }

    @SuppressLint("SuspiciousIndentation")
    override fun getAttendances(selectedDate: String): Flow<ResourceState<List<AttendanceMonitorCellModel>>> {
        val dbAttendances = db.collection("Attendances")
            .whereEqualTo("dateTime", selectedDate)
        val dbUsers = db.collection("Users")
        return flow {
            emit(value = ResourceState.LOADING())
            val results: MutableList<AttendanceMonitorCellModel> = mutableListOf()
            val users = dbUsers.get().await()
            val attendances = dbAttendances.get().await()
            for (document in attendances) {
                val data = document.data
                val id = document.id

                val attendace = AttendanceMonitorCellModel(
                    uid = data["uid"] as String,
                    id = id,
                    division = "",
                    attendanceType = data["type"] as String,
                    date = (data["createAt"] as Timestamp).toDate()
                        .toString(pattern = "yyyy-MM-dd HH:mm:ss"),
                    userName = "",
                    reasonOfPermission = data["reasonOfPermission"] as String,
                )

                for (document in users) {
                    val data = document.toObject(User::class.java)

                    if (data.uid == attendace.uid) {
                        attendace.userName = data.name ?: "-"
                        attendace.division = data.division ?: "-"
                    }
                }
                results.add(attendace)
            }

            emit(value = ResourceState.SUCCESS(data = results.toList()))
        }.catch {
            emit(value = ResourceState.ERROR(it.localizedMessage.toString()))
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getHistories(): Flow<ResourceState<List<AttendanceHistoryModel>>> {
        val fourteenDaysAgo = Date.from(Instant.now().minus(14, ChronoUnit.DAYS))
        val currentUser = firebaseAuth.currentUser!!
        val dbAttendances = db.collection("Attendances")
            .whereEqualTo("uid", currentUser.uid)
            .whereGreaterThanOrEqualTo("createAt", fourteenDaysAgo)
        return flow {
            emit(value = ResourceState.LOADING())
            val attendances = dbAttendances.get().await()
            val results: MutableList<AttendanceHistoryModel> = mutableListOf()
            val datePattern = "yyyy-MM-dd HH:mm:ss"

            for (document in attendances) {
                val data = document.data
                val id = document.id
                val attendance = document.toObject(Attendance::class.java)

                val attendanceHistoryModel = AttendanceHistoryModel(
                    id = id,
                    date = attendance.dateTime,
                    dateStart = attendance.createAt.toDate().toString(datePattern),
                    reasonOfPermission = attendance.reasonOfPermission ?: "",
                    symptomsOfIllness = attendance.symptomsOfIllness ?: listOf(),
                    attendanceType = when (attendance.type) {
                        "MASUK" -> AttendanceType.PRESENT
                        "SAKIT" -> AttendanceType.SICK
                        else -> AttendanceType.PERMIT
                    },
                    dateFinish = ""

                )
                val sameElement = results.firstOrNull { it.date == attendanceHistoryModel.date }
                if (sameElement != null) {
                    if (isDateGreaterThan(
                            sameElement.dateStart,
                            attendanceHistoryModel.dateStart,
                            datePattern
                        )
                    ) {
                        sameElement.dateFinish = sameElement.dateStart
                        sameElement.dateStart = attendanceHistoryModel.dateStart
                    } else {
                        sameElement.dateFinish = attendanceHistoryModel.dateStart
                    }
                } else {
                    results.add(attendanceHistoryModel)
                }
            }

            emit(value = ResourceState.SUCCESS(data = results.toList()))
        }.catch {
            emit(value = ResourceState.ERROR(it.localizedMessage.toString()))
        }
    }

    override fun checkIsAbleToDoAttendance(): Flow<ResourceState<Boolean>> {
        val today = Date()
        val todayString = today.toString("dd-MM-yyyy")
        val currentUser = firebaseAuth.currentUser!!
        val dbAttendances = db.collection("Attendances")
            .whereEqualTo("uid", currentUser.uid)
            .whereEqualTo("dateTime", todayString)
        return flow {
            emit(value = ResourceState.LOADING())
            val attendances = dbAttendances.get().await()
            val results: MutableList<Attendance> = mutableListOf()

            for (document in attendances) {
                results.add(document.toObject(Attendance::class.java))
            }

            Log.d("ATTD", results.toString())
            if ((results.isEmpty() || results.count { element -> element.type == "MASUK" } < 2) && results.count { element -> element.type == "SAKIT" || element.type == "IZIN" } < 1) {
                emit(value = ResourceState.SUCCESS(data = true))
            } else {
                emit(value = ResourceState.ERROR("Mohon maaf anda sudah melakukan Absensi"))
            }
        }.catch {
            emit(value = ResourceState.ERROR(it.localizedMessage.toString()))
        }
    }

    override fun getUsers(idDivision: String): Flow<ResourceState<List<User>>> {
        val dbDivision = db.collection("Divisions")
            .document(idDivision)

        val dbUsers = db.collection("Users")
            .whereNotEqualTo("role", "SUPERVISOR")

        return flow {
            emit(value = ResourceState.LOADING())
            val division = dbDivision.get().await()
                .toObject(DivisionResponse::class.java)
            val results: MutableList<User> = mutableListOf()
            Log.d("DIVISION", division?.name ?: "-")
            val users = dbUsers.get().await()
            for (document in users) {
                val data = document.toObject(User::class.java)
                if (data.division == (division?.name ?: "NULL")) {
                    results.add(data)
                }
            }

            emit(value = ResourceState.SUCCESS(data = results.toList()))
        }.catch {
            emit(value = ResourceState.ERROR(it.localizedMessage.toString()))
        }
    }
    override fun checkIsHasDoAttendance(): Flow<ResourceState<Boolean>> {
        val today = Date()
        val todayString = today.toString("dd-MM-yyyy")
        val currentUser = firebaseAuth.currentUser!!
        val dbAttendances = db.collection("Attendances")
            .whereEqualTo("uid", currentUser.uid)
            .whereEqualTo("dateTime", todayString)
        return flow {
            emit(value = ResourceState.LOADING())
            val attendances = dbAttendances.get().await()
            val results: MutableList<Attendance> = mutableListOf()

            for (document in attendances) {
                results.add(document.toObject(Attendance::class.java))
            }

            Log.d("ATTD", results.toString())
            if (results.count { element -> element.type == "MASUK" } > 0) {
                emit(value = ResourceState.SUCCESS(data = true))
            } else {
                emit(value = ResourceState.ERROR("Mohon maaf anda belum melakukan Absensi"))
            }
        }.catch {
            emit(value = ResourceState.ERROR(it.localizedMessage.toString()))
        }
    }
    @SuppressLint("SimpleDateFormat")
    private fun isDateGreaterThan(
        dateString1: String,
        dateString2: String,
        format: String
    ): Boolean {
        val sdf = SimpleDateFormat(format)
        val date1 = sdf.parse(dateString1)
        val date2 = sdf.parse(dateString2)

        return date1.after(date2)
    }
}