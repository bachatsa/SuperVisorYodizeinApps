package com.ydzmobile.supervisor.ui.screen.auth.login

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.example.supervisoryodizeinapps.R
import com.ydzmobile.supervisor.core.data.AuthState
import com.ydzmobile.supervisor.core.viewModel.LoginUIState
import com.ydzmobile.supervisor.ui.component.atom.button.YMBorderedButton
import com.ydzmobile.supervisor.ui.component.atom.textfield.YMTextField
import com.ydzmobile.supervisor.ui.component.molecule.auth.AuthBanner
import com.ydzmobile.supervisor.ui.navigation.MAIN_GRAPH_ROUTE
import com.ydzmobile.supervisor.ui.navigation.Screen
import com.ydzmobile.supervisor.ui.theme.*

@Composable
fun LoginScreen(
    navController: NavController,
    uiState: LoginUIState,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onLoginPressed: () -> Unit,
) {
    val context = LocalContext.current

    LaunchedEffect(key1 = uiState.authState) {
        if (uiState.authState == AuthState.LOGIN) {
            navController.popBackStack()
            navController.navigate(MAIN_GRAPH_ROUTE)
        } else {
            Toast.makeText(
                context,
                uiState.errorMessage,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 38.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Spacer(modifier = Modifier.weight(1f))
        AuthBanner(
            painterResource(id = R.drawable.ic_signin),
            stringResource(id = R.string.loginScreen_title)
        )

        YMTextField(
            value = uiState.email,
            hint = "Email",
            onValueChange = onEmailChanged,
            foregroundColor = littleBoyBlue,
            borderColor = littleBoyBlue
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            YMTextField(
                value = uiState.password,
                hint = "Password",
                onValueChange = onPasswordChanged,
                foregroundColor = littleBoyBlue,
                borderColor = littleBoyBlue,
                isPasswordField = true
            )

            ClickableText(
                modifier = Modifier,
                text = AnnotatedString(stringResource(id = R.string.forgotPassword_title)),
                onClick = {
                    navController.navigate(Screen.ForgotPassword.route)
                }
            )
        }

        YMBorderedButton(
            modifier = Modifier.padding(horizontal = 36.dp),
            title = stringResource(id = R.string.login_button_title),
            titleSize = 16,
            backgroundColor = littleBoyBlue,
            buttonHeight = 42,
            foregroundColor = Color.White,
            cornerRadius = 16,
            onClick = onLoginPressed)

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = stringResource(id = R.string.register_button_title),
                style = poppinsFont(size = 14),
            )

            ClickableText(
                modifier = Modifier,
                text = AnnotatedString(stringResource(id = R.string.register_button_title_clickable)),
                style = poppinsFont(size = 14, color = littleBoyBlue),
                onClick = {
                    navController.navigate(Screen.Register.route)
                }
            )
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}

@Preview(
    showBackground = true
)
@Composable
private fun LoginScreenPreview() {
    val nav = rememberNavController()

    LoginScreen(nav, uiState = LoginUIState(
        email = "",
        password = ""
    ), {}, {}, {})
}