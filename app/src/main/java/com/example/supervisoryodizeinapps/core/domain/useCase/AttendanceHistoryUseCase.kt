package com.example.supervisoryodizeinapps.core.domain.useCase

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.supervisoryodizeinapps.core.data.ResourceState
import com.example.supervisoryodizeinapps.core.data.database.DatabaseRepositoryImpl
import com.example.supervisoryodizeinapps.core.domain.model.AttendanceHistoryModel
import com.example.supervisoryodizeinapps.core.domain.model.attendanceMonitor.AttendanceMonitorCellModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AttendanceHistoryUseCase @Inject constructor(
    private val databaseRepositoryImpl: DatabaseRepositoryImpl
){

    @RequiresApi(Build.VERSION_CODES.O)
    fun getHistories(): Flow<ResourceState<List<AttendanceHistoryModel>>> = databaseRepositoryImpl.getHistories()

    fun getAttendances(selectedDate: String): Flow<ResourceState<List<AttendanceMonitorCellModel>>> = databaseRepositoryImpl.getAttendances(selectedDate)
}