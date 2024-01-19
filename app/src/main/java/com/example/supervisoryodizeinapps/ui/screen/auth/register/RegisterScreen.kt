package com.ydzmobile.supervisor.ui.screen.auth.register

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.supervisoryodizeinapps.R
import com.ydzmobile.supervisor.core.data.AuthState
import com.ydzmobile.supervisor.core.viewModel.RegisterUIState
import com.ydzmobile.supervisor.ui.component.atom.button.YMBorderedButton
import com.ydzmobile.supervisor.ui.component.atom.textfield.YMTextField
import com.ydzmobile.supervisor.ui.component.molecule.auth.AuthBanner
import com.ydzmobile.supervisor.ui.theme.*

@Composable
fun RegisterScreen(
    navController: NavController,
    uiState: RegisterUIState,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onPasswordConfirmChanged: (String) -> Unit,
    onRoleSelected: (String) -> Unit,
    onRegisterPressed: () -> Unit,
) {

    LaunchedEffect(key1 = uiState.registerState) {
        if (uiState.registerState == AuthState.LOGIN) {
            navController.popBackStack()
        }


    }
    Box(modifier = Modifier
        .fillMaxSize()){
    Column(
        modifier = Modifier
            .padding(horizontal = 38.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Spacer(modifier = Modifier.weight(1f))
        AuthBanner(
            painterResource(id = R.drawable.ic_signup),
            stringResource(id = R.string.register_user)
        )

        YMTextField(
            value = uiState.email,
            hint = "Email",
            onValueChange = onEmailChanged,
            foregroundColor = littleBoyBlue,
            borderColor = littleBoyBlue,
        )

        YMTextField(
            value = uiState.password,
            hint = "Password",
            onValueChange = onPasswordChanged,
            foregroundColor = littleBoyBlue,
            borderColor = littleBoyBlue,
            isPasswordField = true
        )

        YMTextField(
            value = uiState.passwordConfirm,
            hint = "Confirm Password",
            onValueChange = onPasswordConfirmChanged,
            foregroundColor = littleBoyBlue,
            borderColor = littleBoyBlue,
            isPasswordField = true
        )

        DropdownRoles(
            modifier = Modifier,
            onDropDownSelected = onRoleSelected,
            options = listOf("Supervisor", "Employee"),
            selectedDropDown = uiState.role
        )

        YMBorderedButton(
            modifier = Modifier.padding(horizontal = 36.dp),
            title = stringResource(id = R.string.register),
            titleSize = 16,
            backgroundColor = littleBoyBlue,
            buttonHeight = 42,
            foregroundColor = Color.White,
            cornerRadius = 16,
            enabled = uiState.isValidForm,
            onClick = onRegisterPressed)

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = stringResource(id = R.string.already_have_account),
                style = poppinsFont(size = 14),
            )

            ClickableText(
                modifier = Modifier,
                text = AnnotatedString(stringResource(id = R.string.already_have_account_clickable)),
                style = poppinsFont(size = 14, color = littleBoyBlue),
                onClick = {
                    navController.popBackStack()
                }
            )
        }

        Spacer(modifier = Modifier.weight(1f))
    }
    }
}


@Composable
private fun DropdownRoles(
    selectedDropDown: String,
    options: List<String>,
    onDropDownSelected: (String) -> Unit,
    modifier: Modifier
) {
    var isActive by remember { mutableStateOf(false) }
    val title = selectedDropDown
    Box(
        modifier = modifier
            .wrapContentSize(Alignment.TopStart)
    ) {
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .border(2.dp, color = littleBoyBlue, shape = RoundedCornerShape(16.dp))
                .height(54.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White
            ),
            contentPadding = PaddingValues(horizontal = 14.dp),
            onClick = {
                isActive = !isActive
            },
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                Text(
                    text = title.ifEmpty { "Role" },
                    style = poppinsFont(size = 16, color = littleBoyBlue, fontWeight = 700)
                )

                Spacer(modifier = Modifier.weight(1f))

                Icon(
                    modifier = Modifier.size(14.dp),
                    painter = painterResource(id = R.drawable.ic_chevron_down),
                    contentDescription = null,
                    tint = littleBoyBlue
                )
            }
        }

        DropdownMenu(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White.copy(0.8f)),
            expanded = isActive,
            onDismissRequest = { isActive = false }
        ) {
            options.forEach {
                DropdownMenuItem(
                    modifier = Modifier.background(Color.Transparent),
                    text = {
                        Text(
                            text = it,
                            style = poppinsFont(size = 11, fontWeight = 400)
                        )
                    },
                    onClick = {
                        onDropDownSelected(it)
                        isActive = !isActive
                    },
                )
            }
        }
    }
}

@Preview(
    showBackground = true
)
@Composable
private fun RegisterScreenPreview() {
    val nav = rememberNavController()

    RegisterScreen(nav, uiState = RegisterUIState(
        email = "",
        password = "",
        passwordConfirm = "",
    ), {}, {}, {}, {}, {})
}