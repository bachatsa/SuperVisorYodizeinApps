package com.example.supervisoryodizeinapps.core.domain.useCase

import com.example.supervisoryodizeinapps.core.data.auth.AuthRepositoryImpl
import javax.inject.Inject

class ChangePasswordUseCase @Inject constructor(
    private val authRepositoryImpl: AuthRepositoryImpl
) {
    fun changePassword(newPassword: String) = authRepositoryImpl.changePassword(newPassword)
}