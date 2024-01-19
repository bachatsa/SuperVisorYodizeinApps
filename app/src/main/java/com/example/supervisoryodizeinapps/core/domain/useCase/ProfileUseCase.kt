package com.ydzmobile.supervisor.core.domain.useCase

import android.net.Uri
import com.ydzmobile.supervisor.core.data.ResourceState
import com.ydzmobile.supervisor.core.data.auth.AuthRepositoryImpl
import com.ydzmobile.supervisor.core.data.database.DatabaseRepositoryImpl
import com.ydzmobile.supervisor.core.data.remoteStorage.RemoteStorageImpl
import com.ydzmobile.supervisor.core.domain.model.auth.User
import com.ydzmobile.supervisor.core.domain.model.division.Division
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