package com.example.supervisoryodizeinapps.core.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.supervisoryodizeinapps.core.data.ResourceState
import com.example.supervisoryodizeinapps.core.domain.model.TargetListCellModel
import com.example.supervisoryodizeinapps.core.domain.model.division.Division
import com.example.supervisoryodizeinapps.core.domain.useCase.HomeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeUseCase: HomeUseCase
): ViewModel() {
    private var _uiState = MutableStateFlow(HomeUIState())
    var uiState = _uiState.asStateFlow()

    init {
        getDivisions()
    }
    fun getDivisions() {
        homeUseCase.getDivisions().onEach { result ->
            when (result) {
                is ResourceState.SUCCESS -> {
                    _uiState.update {
                        it.copy(
                            divisionList = result.data!!
                        )
                    }
                    Log.d("getDivisions", "SUCCESS")
                }

                is ResourceState.ERROR -> {
                    Log.d("getDivisions", "ERROR")
                }

                is ResourceState.LOADING -> {
                    Log.d("getDivisions", "LOADING")
                }

                else -> {}
            }
        }.launchIn(viewModelScope)
    }
}

data class  HomeUIState(
    val targetList: List<TargetListCellModel> = listOf(),
    val divisionList: List<Division> = listOf()
)