package com.ydzmobile.supervisor.core.domain.useCase

import com.ydzmobile.supervisor.core.data.ResourceState
import com.ydzmobile.supervisor.core.data.database.DatabaseRepositoryImpl
import com.ydzmobile.supervisor.core.domain.model.attendance.Attendance
import com.ydzmobile.supervisor.core.domain.model.auth.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class AttendanceUseCase @Inject constructor(
    private val databaseRepositoryImpl: DatabaseRepositoryImpl
) {
    fun doAttendance(attendance: Attendance): Flow<ResourceState<Boolean>> = databaseRepositoryImpl.doAttendance(attendance)
    fun checkIsAbleToDoAttendance(): Flow<ResourceState<Boolean>> = databaseRepositoryImpl.checkIsAbleToDoAttendance()
}