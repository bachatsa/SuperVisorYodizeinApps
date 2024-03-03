package com.example.supervisoryodizeinapps.core.domain.useCase

import com.google.firebase.auth.AuthResult
import com.example.supervisoryodizeinapps.core.data.ResourceState
import com.example.supervisoryodizeinapps.core.data.auth.AuthRepositoryImpl
import com.example.supervisoryodizeinapps.core.data.database.DatabaseRepositoryImpl
import com.example.supervisoryodizeinapps.core.domain.model.auth.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val authRepositoryImpl: AuthRepositoryImpl,
    private val databaseRepository: DatabaseRepositoryImpl
) {
    fun registerUser(email: String, password: String): Flow<ResourceState<AuthResult>> = authRepositoryImpl.registerUser(email, password)
    fun createUser(user: User): Flow<ResourceState<Boolean>> = databaseRepository.createUser(user)
}