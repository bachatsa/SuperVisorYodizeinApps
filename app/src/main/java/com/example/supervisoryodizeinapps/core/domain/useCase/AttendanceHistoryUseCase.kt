package com.ydzmobile.supervisor.core.domain.useCase

import android.os.Build
import androidx.annotation.RequiresApi
import com.ydzmobile.supervisor.core.data.ResourceState
import com.ydzmobile.supervisor.core.data.database.DatabaseRepositoryImpl
import com.ydzmobile.supervisor.core.domain.enum.AttendanceType
import com.ydzmobile.supervisor.core.domain.model.AttendanceHistoryModel
import com.ydzmobile.supervisor.core.domain.model.attendanceMonitor.AttendanceMonitorCellModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AttendanceHistoryUseCase @Inject constructor(
    private val databaseRepositoryImpl: DatabaseRepositoryImpl
){

    @RequiresApi(Build.VERSION_CODES.O)
    fun getHistories(): Flow<ResourceState<List<AttendanceHistoryModel>>> = databaseRepositoryImpl.getHistories()

    fun getAttendances(selectedDate: String): Flow<ResourceState<List<AttendanceMonitorCellModel>>> = databaseRepositoryImpl.getAttendances(selectedDate)
}