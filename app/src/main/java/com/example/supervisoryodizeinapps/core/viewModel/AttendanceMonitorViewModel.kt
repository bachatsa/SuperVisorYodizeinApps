package com.ydzmobile.supervisor.core.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ydzmobile.supervisor.core.data.AuthState
import com.ydzmobile.supervisor.core.data.ResourceState
import com.ydzmobile.supervisor.core.domain.model.attendanceMonitor.AttendanceMonitorCellModel
import com.ydzmobile.supervisor.core.domain.useCase.AttendanceHistoryUseCase
import com.ydzmobile.supervisor.core.extension.dateStringToLong
import com.ydzmobile.supervisor.core.extension.longToDateStr
import com.ydzmobile.supervisor.core.extension.toDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AttendanceMonitorViewModel @Inject constructor(
    private val useCase: AttendanceHistoryUseCase
) : ViewModel() {
    private var _uiState = MutableStateFlow(AttendanceMonitorUIState())
    var uiState = _uiState.asStateFlow()

    init {
        getAttendances()
    }

    fun getAttendances() {
        useCase.getAttendances(_uiState.value.selectedDate)
            .onEach { result ->
                when (result) {
                    is ResourceState.SUCCESS -> {
                        _uiState.update {
                            it.copy(attendances = result.data!!.sortedByDescending { element ->
                                element.date.toDate("yyyy-MM-dd HH:mm:ss")
                            })
                        }
                        Log.d("getAttendances", "SUCCESS")
                    }

                    is ResourceState.ERROR -> {
                        Log.d("getAttendances", "ERROR")
                    }

                    is ResourceState.LOADING -> {
                        Log.d("getAttendances", "LOADING")
                    }

                    else -> {}
                }
            }.launchIn(viewModelScope)
    }

    fun onSelectedDateChanged(newValue: String) {
        _uiState.update {
            it.copy(
                selectedDate = newValue
            )
        }
        getAttendances()
    }
}

data class AttendanceMonitorUIState(
    val attendances: List<AttendanceMonitorCellModel> = listOf(),
    val selectedDate: String = System.currentTimeMillis().longToDateStr()
)