package com.ydzmobile.supervisor.core.data.remoteStorage

import android.net.Uri
import com.ydzmobile.supervisor.core.data.ResourceState
import com.ydzmobile.supervisor.core.domain.model.auth.User
import kotlinx.coroutines.flow.Flow

interface RemoteStorage {
    fun uploadProfilePicture(uri: Uri): Flow<ResourceState<String>>

    fun getPictureDownloadURL(path: String): Flow<ResourceState<Uri>>
}