package com.example.supervisoryodizeinapps.core.domain.useCase

import com.example.supervisoryodizeinapps.core.data.ResourceState
import com.example.supervisoryodizeinapps.core.data.database.DatabaseRepositoryImpl
import com.example.supervisoryodizeinapps.core.domain.model.attendance.Attendance
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class AttendanceUseCase @Inject constructor(
    private val databaseRepositoryImpl: DatabaseRepositoryImpl
) {
    fun doAttendance(attendance: Attendance): Flow<ResourceState<Boolean>> = databaseRepositoryImpl.doAttendance(attendance)
    fun checkIsAbleToDoAttendance(): Flow<ResourceState<Boolean>> = databaseRepositoryImpl.checkIsAbleToDoAttendance()
}