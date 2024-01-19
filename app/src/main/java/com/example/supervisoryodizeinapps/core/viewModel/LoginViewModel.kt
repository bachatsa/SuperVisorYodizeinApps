package com.ydzmobile.supervisor.core.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ydzmobile.supervisor.core.data.AuthState
import com.ydzmobile.supervisor.core.data.ResourceState
import com.ydzmobile.supervisor.core.domain.model.auth.User
import com.ydzmobile.supervisor.core.domain.useCase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val useCase: LoginUseCase
): ViewModel() {
    private var _uiState = MutableStateFlow(LoginUIState())
    var uiState = _uiState.asStateFlow()

    init {
        checkIsUserLogined()
    }
    fun loginUser() {
        useCase
            .loginUser(email = _uiState.value.email, _uiState.value.password)
            .onEach { result ->
                when (result) {
                    is ResourceState.SUCCESS -> {
                        _uiState.update {
                            it.copy(authState = AuthState.LOGIN)
                        }
                        Log.d("loginUser", "SUCCESS")
                    }

                    is ResourceState.ERROR -> {
                        _uiState.update {
                            it.copy(authState = AuthState.FAILED, errorMessage = result.message ?: "")
                        }
                        Log.d("loginUser", "ERROR")
                    }

                    is ResourceState.LOADING -> {
                        Log.d("loginUser", "LOADING")
                    }
                }
        }.launchIn(viewModelScope)
    }

    private fun checkIsUserLogined() {
        useCase
            .checkIsUserLogined()
            .onEach { result ->
                when (result) {
                    is ResourceState.SUCCESS -> {
                        if (result.data!!){
                            _uiState.update {
                                it.copy(authState = AuthState.LOGIN)
                            }
                        } else {
                            _uiState.update {
                                it.copy(authState = AuthState.NOT_LOGIN)
                            }
                        }
                        Log.d("checkIsUserLogined", "SUCCESS")
                    }

                    is ResourceState.ERROR -> {
                        _uiState.update {
                            it.copy(authState = AuthState.FAILED, errorMessage = result.message ?: "")
                        }
                        Log.d("checkIsUserLogined", "ERROR")
                    }

                    is ResourceState.LOADING -> {
                        Log.d("checkIsUserLogined", "LOADING")
                    }
                }
        }.launchIn(viewModelScope)
    }

    fun onEmailChanged(newValue: String) {
        _uiState.update {
            it.copy(
                email = newValue
            )
        }
    }

    fun onPasswordChanged(newValue: String) {
        _uiState.update {
            it.copy(
                password = newValue
            )
        }
    }
}

data class LoginUIState(
    val email: String = "",
    val password: String = "",
    val errorMessage: String = "",
    val authState: AuthState = AuthState.NOT_LOGIN,
)