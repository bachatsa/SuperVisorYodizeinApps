package com.example.supervisoryodizeinapps.core.domain.useCase

import com.example.supervisoryodizeinapps.core.data.ResourceState
import com.example.supervisoryodizeinapps.core.data.auth.AuthRepositoryImpl
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ForgotPasswordUseCase @Inject constructor(
    private val authRepositoryImpl: AuthRepositoryImpl
) {
    fun forgotPassword(email: String): Flow<ResourceState<Boolean>> = authRepositoryImpl.forgotPassword(email)
}
