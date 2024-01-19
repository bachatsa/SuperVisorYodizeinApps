package com.ydzmobile.supervisor.core.data.database

import com.ydzmobile.supervisor.core.data.ResourceState
import com.ydzmobile.supervisor.core.domain.model.AttendanceHistoryModel
import com.ydzmobile.supervisor.core.domain.model.attendance.Attendance
import com.ydzmobile.supervisor.core.domain.model.attendanceMonitor.AttendanceMonitorCellModel
import com.ydzmobile.supervisor.core.domain.model.auth.User
import com.ydzmobile.supervisor.core.domain.model.division.Division
import com.ydzmobile.supervisor.core.domain.model.monitor.TargetModel
import com.ydzmobile.supervisor.core.domain.model.monitor.TargetModelCell
import com.ydzmobile.supervisor.core.domain.model.monitor.TargetModelRequest
import kotlinx.coroutines.flow.Flow

interface DatabaseRepository {
    fun createUser(user: User): Flow<ResourceState<Boolean>>

    fun doAttendance(attendance: Attendance): Flow<ResourceState<Boolean>>

    fun getCurrentUser(): Flow<ResourceState<User>>
    fun updateCurrentUser(user: User): Flow<ResourceState<Boolean>>

    fun getDivisions(): Flow<ResourceState<List<Division>>>
    fun getTargets(division: String): Flow<ResourceState<List<TargetModelCell>>>
    fun createTargets(division: String, target: TargetModelRequest): Flow<ResourceState<Boolean>>
    fun deleteTarget(target: TargetModelRequest, idTarget: String): Flow<ResourceState<Boolean>>
    fun updateTarget(target: TargetModelRequest, idTarget: String): Flow<ResourceState<Boolean>>

    fun getAttendances(selectedDate: String): Flow<ResourceState<List<AttendanceMonitorCellModel>>>

    fun getHistories(): Flow<ResourceState<List<AttendanceHistoryModel>>>

    fun checkIsAbleToDoAttendance(): Flow<ResourceState<Boolean>>

    fun getUsers(idDivision: String): Flow<ResourceState<List<User>>>
}