package com.example.supervisoryodizeinapps.core.viewModel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.supervisoryodizeinapps.core.data.ResourceState
import com.example.supervisoryodizeinapps.core.domain.model.AttendanceHistoryModel
import com.example.supervisoryodizeinapps.core.domain.useCase.AttendanceHistoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class AttendanceHistoryViewModel @Inject constructor(
    private val useCase: AttendanceHistoryUseCase
): ViewModel() {
    private var _uiState = MutableStateFlow(AttendanceHistoryUIState())
    var uiState = _uiState.asStateFlow()

    init {
        getHistories()
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun getHistories() {
        useCase.getHistories().onEach { result ->
            when (result) {
                is ResourceState.SUCCESS -> {
                    _uiState.update {
                        it.copy(
                            histories = result.data!!
                        )
                    }
                    Log.d("getHistories", "SUCCESS")
                }

                is ResourceState.ERROR -> {
                    Log.d("getHistories", "ERROR")
                }

                is ResourceState.LOADING -> {
                    Log.d("getHistories", "LOADING")
                }

                else -> {}
            }
        }.launchIn(viewModelScope)
    }
}

data class AttendanceHistoryUIState (
    val histories: List<AttendanceHistoryModel> = listOf()
)