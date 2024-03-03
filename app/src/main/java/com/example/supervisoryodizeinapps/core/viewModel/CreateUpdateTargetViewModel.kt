package com.example.supervisoryodizeinapps.core.viewModel


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.supervisoryodizeinapps.core.data.ResourceState
import com.example.supervisoryodizeinapps.core.domain.model.auth.User
import com.example.supervisoryodizeinapps.core.domain.model.monitor.TargetModel
import com.example.supervisoryodizeinapps.core.domain.model.monitor.TargetModelRequest
import com.example.supervisoryodizeinapps.core.domain.useCase.CreateUpdateTargetUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CreateUpdateTargetViewModel @Inject constructor(
    private val useCase: CreateUpdateTargetUseCase
): ViewModel() {
    private var _uiState = MutableStateFlow(CreateUpdateUIState())
    var uiState = _uiState.asStateFlow()

    init {

    }
    fun onSelectedUser(newValue: User) {
        _uiState.update {
            it.copy(
                selectedUser = newValue
            )
        }
        checkIsValid()
    }

    fun onTotalTarget(newValue: String) {
        _uiState.update {
            it.copy(
                totalTarget = newValue
            )
        }
        checkIsValid()
    }

    fun onProductType(newValue: String) {
        _uiState.update {
            it.copy(
                productType = newValue
            )
        }
        checkIsValid()
    }

    fun onDateStart(newValue: String) {
        _uiState.update {
            it.copy(
                dateStart = newValue
            )
        }
        checkIsValid()
    }

    fun onDateEnd(newValue: String) {
        _uiState.update {
            it.copy(
                dateEnd = newValue
            )
        }
        checkIsValid()
    }

    fun onTargetType(newValue: String) {
        _uiState.update {
            it.copy(
                targetType = newValue
            )
        }
        checkIsValid()
    }

    fun setupId(divisionId: String, targetId: String = "") {
        _uiState.update {
            it.copy(
                divisionId = divisionId,
                targetId = targetId,
                isEditMode = targetId.isNotEmpty()
            )
        }
        checkIsValid()
        getUsers()
    }

    fun setupUsingModel(target: TargetModel) {
        if(!_uiState.value.isLoaded){
            _uiState.update {
                it.copy(
                    divisionId = target.idDivision,
                    targetId = target.id,
                    selectedUser = it.selectedUser.copy(
                        idEmployee = target.idEmployee
                    ),
                    totalTarget = target.totalTarget.toString(),
                    productType = target.productType,
                    dateStart = target.dateStart,
                    dateEnd = target.dateFinish,
                    isEditMode = true,
                    isLoaded = target.idEmployee.isNotEmpty(),
                    targetType = target.targetType
                )
            }
        }
        checkIsValid()
        getUsers()
    }

    fun createTarget() {
        val target = TargetModelRequest(
            idEmployee = _uiState.value.selectedUser.idEmployee ?: "",
            idDivision = _uiState.value.divisionId,
            totalTarget = _uiState.value.totalTarget.toInt(),
            productType = _uiState.value.productType,
            dateStart = _uiState.value.dateStart,
            dateFinish = _uiState.value.dateEnd,
            targetType = _uiState.value.targetType,
        )
        useCase
            .createTargets(_uiState.value.divisionId, target)
            .onEach { result ->
                when (result) {
                    is ResourceState.SUCCESS -> {
                        _uiState.update {
                            it.copy(isSuccess = true)
                        }
                        Log.d("loginUser", "SUCCESS")
                    }

                    is ResourceState.ERROR -> {
                        _uiState.update {
                            it.copy(isSuccess = false)
                        }
                        Log.d("loginUser", "ERROR")
                    }

                    is ResourceState.LOADING -> {
                        Log.d("loginUser", "LOADING")
                    }

                    else -> {}
                }
            }.launchIn(viewModelScope)
    }

    fun updateTarget() {
        val target = TargetModelRequest(
            idEmployee = _uiState.value.selectedUser.idEmployee ?: "",
            idDivision = _uiState.value.divisionId,
            totalTarget = _uiState.value.totalTarget.toInt(),
            productType = _uiState.value.productType,
            dateStart = _uiState.value.dateStart,
            dateFinish = _uiState.value.dateEnd,
            targetType = _uiState.value.targetType,
        )
        useCase
            .updateTarget(target = target, idTarget = _uiState.value.targetId)
            .onEach { result ->
                when (result) {
                    is ResourceState.SUCCESS -> {
                        _uiState.update {
                            it.copy(isSuccess = true)
                        }
                        checkIsValid()
                        Log.d("loginUser", "SUCCESS")
                    }

                    is ResourceState.ERROR -> {
                        _uiState.update {
                            it.copy(isSuccess = false)
                        }
                        Log.d("loginUser", "ERROR")
                    }

                    is ResourceState.LOADING -> {
                        Log.d("loginUser", "LOADING")
                    }

                    else -> {}
                }
            }.launchIn(viewModelScope)
    }
    fun deleteTarget() {
        val target = TargetModelRequest(
            idEmployee = _uiState.value.selectedUser.idEmployee ?: "",
            idDivision = _uiState.value.divisionId,
            totalTarget = _uiState.value.totalTarget.toInt(),
            productType = _uiState.value.productType,
            dateStart = _uiState.value.dateStart,
            dateFinish = _uiState.value.dateEnd,
            targetType = _uiState.value.targetType,
        )
        useCase
            .deleteTarget(target = target, idTarget = _uiState.value.targetId)
            .onEach { result ->
                when (result) {
                    is ResourceState.SUCCESS -> {
                        _uiState.update {
                            it.copy(isSuccess = true)
                        }
                        Log.d("loginUser", "SUCCESS")
                        checkIsValid()
                    }

                    is ResourceState.ERROR -> {
                        _uiState.update {
                            it.copy(isSuccess = false)
                        }
                        Log.d("loginUser", "ERROR")
                    }

                    is ResourceState.LOADING -> {
                        Log.d("loginUser", "LOADING")
                    }

                    else -> {}
                }
            }.launchIn(viewModelScope)
    }

    private fun getUsers() {
        useCase
            .getUsers(_uiState.value.divisionId)
            .onEach { result ->
                when (result) {
                    is ResourceState.SUCCESS -> {
                        _uiState.update {
                            it.copy(
                                users = result.data ?: listOf()
                            )
                        }
                        checkIsValid()
                        Log.d("loginUser", "SUCCESS")
                    }

                    is ResourceState.ERROR -> {
                        _uiState.update {
                            it.copy(isSuccess = false)
                        }
                        Log.d("loginUser", "ERROR")
                    }

                    is ResourceState.LOADING -> {
                        Log.d("loginUser", "LOADING")
                    }

                    else -> {}
                }
            }.launchIn(viewModelScope)
    }

    private fun checkIsValid() {
        val isValid: Boolean = _uiState.value.totalTarget.isNotEmpty() &&
                _uiState.value.targetType.isNotEmpty() &&
                _uiState.value.productType.isNotEmpty() &&
                _uiState.value.dateStart.isNotEmpty() &&
                _uiState.value.dateEnd.isNotEmpty() &&
                !_uiState.value.selectedUser.idEmployee.isNullOrEmpty()

        _uiState.update {
            it.copy(
                isValid = isValid
            )
        }
    }
    private fun checkAttendance(){
        useCase
            .checkIsHasDoAttendance()
            .onEach { result ->
                when (result) {
                    is ResourceState.SUCCESS -> {
                        _uiState.update {
                            it.copy(isHasDoAttendance = result.data!!, isLoaded = true)
                        }
                        Log.d("loginUser", "SUCCESS")
                    }

                    is ResourceState.ERROR -> {
                        Log.d("loginUser", "ERROR")
                    }

                    is ResourceState.LOADING -> {
                        Log.d("loginUser", "LOADING")
                    }

                    else -> {}
                }
            }.launchIn(viewModelScope)
    }
}

data class CreateUpdateUIState(
    val divisionId: String = "",
    val targetId: String = "",
    val selectedUser: User = User(uid = "", email = ""),
    val totalTarget: String = "",
    val productType: String = "",
    val dateStart: String = "",
    val dateEnd: String = "",
    val targetType: String = "Primer",
    val isEditMode: Boolean = false,
    val isSuccess: Boolean = false,
    val isLoaded: Boolean = false,
    val users: List<User> = listOf(),
    val isValid: Boolean = false,
    val isHasDoAttendance: Boolean = false,
)