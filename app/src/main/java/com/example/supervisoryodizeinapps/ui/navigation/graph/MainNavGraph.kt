package com.ydzmobile.supervisor.ui.navigation.graph

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.ydzmobile.supervisor.core.domain.enum.PermitType
import com.ydzmobile.supervisor.core.domain.model.monitor.toTarget
import com.ydzmobile.supervisor.core.viewModel.AttendanceViewModel
import com.ydzmobile.supervisor.core.viewModel.ChangePasswordViewModel
import com.ydzmobile.supervisor.core.viewModel.CreateUpdateTargetViewModel
import com.ydzmobile.supervisor.core.viewModel.DivisionDetailViewModel
import com.ydzmobile.supervisor.core.viewModel.PermitViewModel
import com.ydzmobile.supervisor.core.viewModel.ProfileViewModel
import com.ydzmobile.supervisor.ui.component.molecule.main.attendance.AttendanceNavBar
import com.ydzmobile.supervisor.ui.component.molecule.main.target.CreateTargetFloatingButton
import com.ydzmobile.supervisor.ui.navigation.DIVISION_ARGUMENT_KEY
import com.ydzmobile.supervisor.ui.navigation.DIVISION_ID_ARGUMENT_KEY
import com.ydzmobile.supervisor.ui.navigation.MAIN_GRAPH_ROUTE
import com.ydzmobile.supervisor.ui.navigation.Screen
import com.ydzmobile.supervisor.ui.navigation.TARGET_ARGUMENT_KEY
import com.ydzmobile.supervisor.ui.navigation.bottomTabNav.BottomBarScreen
import com.ydzmobile.supervisor.ui.screen.main.MainScreen
import com.ydzmobile.supervisor.ui.screen.main.attendance.AttendanceScreen
import com.ydzmobile.supervisor.ui.screen.main.attendance.permit.PermitScreen
import com.ydzmobile.supervisor.ui.screen.main.home.DivisionDetailScreen
import com.ydzmobile.supervisor.ui.screen.main.profile.changePassword.ChangePasswordScreen
import com.ydzmobile.supervisor.ui.screen.main.profile.changePassword.UpdatePasswordCompleteScreen
import com.ydzmobile.supervisor.ui.screen.main.profile.updateProfile.UpdateProfileScreen
import com.ydzmobile.supervisor.ui.screen.main.target.CreateUpdateTargetScreen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")

fun NavGraphBuilder.mainScreenNavGraph(
    navController: NavHostController
) {
    navigation(
        startDestination = Screen.Main.route,
        route = MAIN_GRAPH_ROUTE
    ) {
        composable(Screen.Main.route) {
            MainScreen(rootNavController = navController)
        }


        composable(route = Screen.Attendance.route) {
            val viewModel = hiltViewModel<AttendanceViewModel>()
            val locationState by viewModel.locationState.collectAsState()
            val uiState by viewModel.uiState.collectAsState()

            AttendanceScreen(
                navController,
                uiState,
                locationState,
                viewModel::handle,
                viewModel::onPresentPressed,
                viewModel::clearToast
            )
        }

        composable(route = Screen.Permit.route) {
            val viewModel = hiltViewModel<PermitViewModel>()
            PermitScreen(
                navController,
                null,
                viewModel::onSickSubmitPressed,
                viewModel::onPermitPressed
            )
        }

        composable(route = Screen.Permit.Sick.route) {
            val viewModel = hiltViewModel<PermitViewModel>()
            PermitScreen(
                navController,
                type = PermitType.SICK,
                viewModel::onSickSubmitPressed,
                viewModel::onPermitPressed
            )
        }

        composable(route = Screen.Permit.Other.route) {
            val viewModel = hiltViewModel<PermitViewModel>()
            PermitScreen(
                navController,
                type = PermitType.OTHER,
                viewModel::onSickSubmitPressed,
                viewModel::onPermitPressed
            )
        }

        composable(route = Screen.ChangePassword.route) {
            val viewModel = hiltViewModel<ChangePasswordViewModel>()
            val uiState by viewModel.uiState.collectAsState()
            Scaffold(
                topBar = {
                    AttendanceNavBar(title = "") {
                        navController.popBackStack()
                    }
                }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it)
                ) {
                    ChangePasswordScreen(
                        navController,
                        uiState,
                        viewModel::onPasswordChanged,
                        viewModel::onPasswordConfirmChanged,
                        viewModel::changePassword
                    )
                }
            }
        }

        composable(
            route = BottomBarScreen.DetailDivision.route,
            arguments = listOf(
                navArgument(DIVISION_ARGUMENT_KEY) {
                    type = NavType.StringType
                },
                navArgument(DIVISION_ID_ARGUMENT_KEY) {
                    type = NavType.StringType
                }
            )
        ) { entry ->
            val viewModel = hiltViewModel<DivisionDetailViewModel>()
            val uiState by viewModel.uiState.collectAsState()
            val arg = entry.arguments?.getString(DIVISION_ARGUMENT_KEY) ?: ""
            val id = entry.arguments?.getString(DIVISION_ID_ARGUMENT_KEY) ?: ""
            viewModel.getTargets(id)
            Scaffold(
                floatingActionButton = {
                    CreateTargetFloatingButton() {
                        navController.navigate("Crate_Target/${id}")
                    }
                }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it)
                ) {
                    DivisionDetailScreen(navController, arg, id, uiState)
                }
            }
        }

        composable(
            route = BottomBarScreen.UpdateTarget.route,
            arguments = listOf(
                navArgument(TARGET_ARGUMENT_KEY) {
                    type = NavType.StringType
                }
            )
        ) { entry ->
            val viewModel = hiltViewModel<CreateUpdateTargetViewModel>()
            val uiState by viewModel.uiState.collectAsState()
            val arg = entry.arguments?.getString(TARGET_ARGUMENT_KEY) ?: ""
            viewModel.setupUsingModel(arg.toTarget()!!)

            CreateUpdateTargetScreen(
                navController = navController,
                uiState = uiState,
                viewModel::onSelectedUser,
                viewModel::onTotalTarget,
                viewModel::onProductType,
                viewModel::onDateStart,
                viewModel::onDateEnd,
                viewModel::onTargetType,
                {},
                viewModel::updateTarget,
                viewModel::deleteTarget,
            )
        }

        composable(
            route = BottomBarScreen.CreateTarget.route,
            arguments = listOf(
                navArgument(DIVISION_ID_ARGUMENT_KEY) {
                    type = NavType.StringType
                }
            )
        ) { entry ->
            val viewModel = hiltViewModel<CreateUpdateTargetViewModel>()
            val uiState by viewModel.uiState.collectAsState()
            val id = entry.arguments?.getString(DIVISION_ID_ARGUMENT_KEY) ?: ""
            viewModel.setupId(id)

            Log.d("totalTarget", id)
            CreateUpdateTargetScreen(
                navController = navController,
                uiState = uiState,
                viewModel::onSelectedUser,
                viewModel::onTotalTarget,
                viewModel::onProductType,
                viewModel::onDateStart,
                viewModel::onDateEnd,
                viewModel::onTargetType,
                viewModel::createTarget,
                {}, {}
            )
        }

        composable(
            route = Screen.UpdateProfile.route
        ) {
            val viewModel = hiltViewModel<ProfileViewModel>()
            val uiState by viewModel.uiState.collectAsState()
            UpdateProfileScreen(
                navController = navController,
                uiState = uiState,
                onAddressChanged = viewModel::onAddressChanged,
                onRtChanged = viewModel::onRtChanged,
                onBirthDateChanged = viewModel::onBirthDateChanged,
                onBloodTypeChanged = viewModel::onBloodTypeChanged,
                onNameChanged = viewModel::onNameChanged,
                onPhoneNumberChanged = viewModel::onPhoneNumberChanged,
                onUpdateProfilePressed = viewModel::updateProfile,
                onSelectedImage = viewModel::onSelectedImage,
            )
        }

        composable(
            route = Screen.UpdatePasswordComplete.route
        ) {
            UpdatePasswordCompleteScreen(navController = navController)
        }
    }
}