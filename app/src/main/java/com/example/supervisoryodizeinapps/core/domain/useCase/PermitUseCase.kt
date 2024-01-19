package com.ydzmobile.supervisor.core.domain.useCase

import com.ydzmobile.supervisor.core.data.ResourceState
import com.ydzmobile.supervisor.core.data.database.DatabaseRepositoryImpl
import com.ydzmobile.supervisor.core.domain.model.attendance.Attendance
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PermitUseCase @Inject constructor(
    private val databaseRepositoryImpl: DatabaseRepositoryImpl
) {
    fun doAttendance(attendance: Attendance): Flow<ResourceState<Boolean>> = databaseRepositoryImpl.doAttendance(attendance)
}