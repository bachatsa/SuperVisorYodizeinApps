package com.ydzmobile.supervisor.ui.navigation.graph

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.ydzmobile.supervisor.ui.screen.auth.register.RegisterScreen
import com.example.supervisoryodizeinapps.R
import com.ydzmobile.supervisor.core.viewModel.ForgotPasswordViewModel
import com.ydzmobile.supervisor.core.viewModel.LoginViewModel
import com.ydzmobile.supervisor.core.viewModel.RegisterViewModel
import com.ydzmobile.supervisor.ui.navigation.AUTH_GRAPH_ROUTE
import com.ydzmobile.supervisor.ui.navigation.Screen
import com.ydzmobile.supervisor.ui.screen.auth.AuthScreenWrapper
import com.ydzmobile.supervisor.ui.screen.auth.forgotPassword.ForgotPasswordScreen
import com.ydzmobile.supervisor.ui.screen.auth.login.LoginScreen


fun NavGraphBuilder.authNavGraph(
    navController: NavHostController,
) {
    navigation(
        startDestination = Screen.Login.route,
        route = AUTH_GRAPH_ROUTE
    ) {

        composable(Screen.Login.route) {
            val viewModel = hiltViewModel<LoginViewModel>()
            val uiState by viewModel.uiState.collectAsState()

            AuthScreenWrapper(
                title = stringResource(id = R.string.auth_navigation_title)
            ) {
                LoginScreen(
                    navController = navController,
                    uiState = uiState,
                    onEmailChanged = viewModel::onEmailChanged,
                    onPasswordChanged = viewModel::onPasswordChanged,
                    onLoginPressed = viewModel::loginUser
                )
            }
        }

        composable(Screen.ForgotPassword.route) {
            val viewModel = hiltViewModel<ForgotPasswordViewModel>()
            val uiState by viewModel.uiState.collectAsState()

            AuthScreenWrapper(
                title = stringResource(id = R.string.auth_navigation_title)
            ) {
                ForgotPasswordScreen(
                    navController = navController,
                    uiState = uiState,
                    onEmailChanged = viewModel::onEmailChanged,
                    viewModel::onSubmitPressed
                )
            }
        }
    }


    composable(Screen.Register.route) {
        val viewModel = hiltViewModel<RegisterViewModel>()
        val uiState by viewModel.uiState.collectAsState()

        AuthScreenWrapper(
            title = stringResource(id = R.string.auth_navigation_title)
        ) {
            RegisterScreen(
                navController = navController,
                uiState = uiState,
                onEmailChanged = viewModel::onEmailChanged,
                onPasswordChanged = viewModel::onPasswordChanged,
                onPasswordConfirmChanged = viewModel::onPasswordConfirmChanged,
                onRegisterPressed = viewModel::registerUser,
                onRoleSelected = viewModel::onRoleSelected
            )
        }
    }
}