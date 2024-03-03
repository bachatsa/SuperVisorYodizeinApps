package com.example.supervisoryodizeinapps.core.data.remoteStorage

import android.net.Uri
import com.example.supervisoryodizeinapps.core.data.ResourceState
import kotlinx.coroutines.flow.Flow

interface RemoteStorage {
    fun uploadProfilePicture(uri: Uri): Flow<ResourceState<String>>

    fun getPictureDownloadURL(path: String): Flow<ResourceState<Uri>>
}