package com.ydzmobile.supervisor.core.domain.useCase

import com.google.firebase.auth.FirebaseAuth
import com.ydzmobile.supervisor.core.data.ResourceState
import com.ydzmobile.supervisor.core.data.auth.AuthRepository
import com.ydzmobile.supervisor.core.data.auth.AuthRepositoryImpl
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ForgotPasswordUseCase @Inject constructor(
    private val authRepositoryImpl: AuthRepositoryImpl
) {
    fun forgotPassword(email: String): Flow<ResourceState<Boolean>> = authRepositoryImpl.forgotPassword(email)
}
