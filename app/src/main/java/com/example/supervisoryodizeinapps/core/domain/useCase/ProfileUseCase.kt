package com.example.supervisoryodizeinapps.core.domain.useCase

import android.net.Uri
import com.example.supervisoryodizeinapps.core.data.ResourceState
import com.example.supervisoryodizeinapps.core.data.auth.AuthRepositoryImpl
import com.example.supervisoryodizeinapps.core.data.database.DatabaseRepositoryImpl
import com.example.supervisoryodizeinapps.core.data.remoteStorage.RemoteStorageImpl
import com.example.supervisoryodizeinapps.core.domain.model.auth.User
import com.example.supervisoryodizeinapps.core.domain.model.division.Division
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProfileUseCase @Inject constructor(
    private val databaseRepositoryImpl: DatabaseRepositoryImpl,
    private val authRepositoryImpl: AuthRepositoryImpl,
    private val storageImpl: RemoteStorageImpl
) {
    fun getCurrentUser(): Flow<ResourceState<User>> = databaseRepositoryImpl.getCurrentUser()
    fun logout(): Flow<ResourceState<Boolean>> = authRepositoryImpl.logout()
    fun updateCurrentUser(user: User): Flow<ResourceState<Boolean>> = databaseRepositoryImpl.updateCurrentUser(user)
    fun uploadProfilePicture(uri: Uri) = storageImpl.uploadProfilePicture(uri)
    fun getPictureDownloadURL(path: String) = storageImpl.getPictureDownloadURL(path)

    fun getDivisions(): Flow<ResourceState<List<Division>>> = databaseRepositoryImpl.getDivisions()
}