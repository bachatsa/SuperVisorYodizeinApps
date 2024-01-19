package com.ydzmobile.supervisor.core.viewModel

import android.net.Uri
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class UpdateWorkProgressViewModel @Inject constructor(

): ViewModel() {
    private var _uiState = MutableStateFlow(UpdateWorkProgressUIState())
    var uiState = _uiState.asStateFlow()
}

data class UpdateWorkProgressUIState(
    val totalWorkBeenDone: String = "",
    val selectedImage: Uri? = null
)