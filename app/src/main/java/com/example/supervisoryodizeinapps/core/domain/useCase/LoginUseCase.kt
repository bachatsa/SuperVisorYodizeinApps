package com.example.supervisoryodizeinapps.core.domain.useCase

import com.google.firebase.auth.AuthResult
import com.example.supervisoryodizeinapps.core.data.auth.AuthRepositoryImpl
import com.example.supervisoryodizeinapps.core.data.ResourceState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepositoryImpl: AuthRepositoryImpl
) {
    fun loginUser(email: String, password: String): Flow<ResourceState<AuthResult>> = authRepositoryImpl.loginUser(email, password)
    fun checkIsUserLogined(): Flow<ResourceState<Boolean>> = authRepositoryImpl.checkIsUserLogined()
}