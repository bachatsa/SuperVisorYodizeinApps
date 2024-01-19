package com.ydzmobile.supervisor.ui.screen.auth

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.UiComposable
import com.ydzmobile.supervisor.ui.component.molecule.auth.AuthNavigationBar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AuthScreenWrapper(
    title: String,
    content: @Composable @UiComposable () -> Unit,
) {
    Scaffold(
        topBar = {
            AuthNavigationBar(title = title)
        }
    ) {
            content()


    }
}