package com.ydzmobile.supervisor.core.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ydzmobile.supervisor.core.data.AuthState
import com.ydzmobile.supervisor.core.data.ResourceState
import com.ydzmobile.supervisor.core.domain.model.monitor.TargetModel
import com.ydzmobile.supervisor.core.domain.model.monitor.TargetModelCell
import com.ydzmobile.supervisor.core.domain.useCase.DivisionDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class DivisionDetailViewModel @Inject constructor(
    private val useCase: DivisionDetailUseCase
): ViewModel() {
    private var _uiState = MutableStateFlow(DivisionDetailUIState())
    var uiState = _uiState.asStateFlow()

    fun getTargets(divisionName: String) {
        useCase
            .getTargets(divisionName)
            .onEach { result ->
                when (result) {
                    is ResourceState.SUCCESS -> {
                        _uiState.update {
                            it.copy(targets = result.data!!)
                        }
                        Log.d("getTargets", "SUCCESS ${result.data!!}")
                    }

                    is ResourceState.ERROR -> {
                        Log.d("getTargets", "ERROR")
                    }

                    is ResourceState.LOADING -> {
                        Log.d("getTargets", "LOADING")
                    }
                }
            }.launchIn(viewModelScope)
    }
}

data class DivisionDetailUIState(
    val targets: List<TargetModelCell> = listOf()
)