package com.ydzmobile.supervisor.core.domain.useCase

import com.ydzmobile.supervisor.core.data.auth.AuthRepositoryImpl
import javax.inject.Inject

class ChangePasswordUseCase @Inject constructor(
    private val authRepositoryImpl: AuthRepositoryImpl
) {
    fun changePassword(newPassword: String) = authRepositoryImpl.changePassword(newPassword)
}