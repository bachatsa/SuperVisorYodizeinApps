package com.example.supervisoryodizeinapps.core.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthResult
import com.example.supervisoryodizeinapps.core.data.AuthState
import com.example.supervisoryodizeinapps.core.data.ResourceState
import com.example.supervisoryodizeinapps.core.domain.model.auth.User
import com.example.supervisoryodizeinapps.core.domain.useCase.RegisterUseCase
import com.example.supervisoryodizeinapps.core.extension.isValidEmail
import com.example.supervisoryodizeinapps.core.extension.isValidPassword
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val useCase: RegisterUseCase
): ViewModel() {
    private var _uiState = MutableStateFlow(RegisterUIState())
    var uiState = _uiState.asStateFlow()

    fun registerUser() {
        useCase
            .registerUser(
                email = _uiState.value.email,
                _uiState.value.password
            ).onEach { result ->
                when (result) {
                    is ResourceState.SUCCESS -> {
                        val authResult: AuthResult = result.data!!
                        createUser(authResult)
                        Log.d("registerUser", "SUCCESS")
                    }

                    is ResourceState.ERROR -> {
                        _uiState.update {
                            it.copy(registerState = AuthState.FAILED)
                        }
                        Log.d("registerUser", "ERROR")
                    }

                    is ResourceState.LOADING -> {
                        Log.d("registerUser", "LOADING")
                    }

                    else -> {}
                }
            }.launchIn(viewModelScope)
    }


    private fun createUser(authResult: AuthResult) {
        val user = User(uid = authResult.user!!.uid, email = _uiState.value.email, role = _uiState.value.role.uppercase())
        useCase
            .createUser(user)
            .onEach { result ->
                when (result) {
                    is ResourceState.SUCCESS -> {
                        _uiState.update {
                            it.copy(registerState = AuthState.LOGIN)
                        }
                        Log.d("createUser", "SUCCESS")
                    }

                    is ResourceState.ERROR -> {
                        _uiState.update {
                            it.copy(
                                registerState = AuthState.FAILED
                            )
                        }
                        Log.d("createUser", "ERROR")
                    }

                    is ResourceState.LOADING -> {
                        Log.d("createUser", "LOADING")
                    }

                    else -> {}
                }
            }.launchIn(viewModelScope)
    }

    fun onEmailChanged(newValue: String) {
        _uiState.update {
            it.copy(
                email = newValue,
                isValidForm = isValidForm()
            )
        }
    }

    fun onPasswordChanged(newValue: String) {
        _uiState.update {
            it.copy(
                password = newValue,
                isValidForm = isValidForm()
            )
        }
    }

    fun onPasswordConfirmChanged(newValue: String) {
        _uiState.update {
            it.copy(
                passwordConfirm = newValue,
                isValidForm = isValidForm()

            )
        }
    }

    fun onRoleSelected(newValue: String) {
        _uiState.update {
            it.copy(
                role = newValue,
                isValidForm = isValidForm()
            )
        }
    }

    fun isValidForm(): Boolean {
        return (_uiState.value.email.isValidEmail() && _uiState.value.password.isValidPassword() && _uiState.value.password == _uiState.value.passwordConfirm) && _uiState.value.role.isNotEmpty()
    }
}

data class RegisterUIState(
    val email: String = "",
    val password: String = "",
    val passwordConfirm: String = "",
    val role: String = "",
    val registerState: AuthState = AuthState.NOT_LOGIN,
    val isValidForm: Boolean = false
)