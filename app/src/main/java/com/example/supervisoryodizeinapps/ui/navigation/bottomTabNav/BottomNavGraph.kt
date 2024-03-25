package com.ydzmobile.supervisor.ui.navigation.bottomTabNav

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.supervisoryodizeinapps.core.viewModel.AttendanceHistoryViewModel
import com.example.supervisoryodizeinapps.core.viewModel.AttendanceMonitorViewModel
import com.example.supervisoryodizeinapps.core.viewModel.HomeViewModel
import com.example.supervisoryodizeinapps.core.viewModel.ProfileViewModel
import com.ydzmobile.supervisor.ui.screen.main.attendanceHisotry.AttendanceHistoryScreen
import com.ydzmobile.supervisor.ui.screen.main.attendanceMonitor.AttendanceMonitorScreen
import com.ydzmobile.supervisor.ui.screen.main.home.HomeScreen
import com.ydzmobile.supervisor.ui.screen.main.profile.ProfileScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BottomNavGraph(
    navController: NavHostController,
    rootNavController: NavController
) {
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Home.route
    ) {
        composable(route = BottomBarScreen.AttendanceMonitor.route)
        {
            val viewModel = hiltViewModel<AttendanceMonitorViewModel>()
            val uiState by viewModel.uiState.collectAsState()

            AttendanceMonitorScreen(navController, uiState, viewModel::onSelectedDateChanged, viewModel::getAttendances, viewModel::clearToast)
        }

        composable(route = BottomBarScreen.Home.route)
        {
            val viewModel = hiltViewModel<HomeViewModel>()
            val uiState by viewModel.uiState.collectAsState()

            HomeScreen(rootNavController, homeUIState = uiState, viewModel::getDivisions)
        }

        composable(route = BottomBarScreen.History.route)
        {
            val viewModel = hiltViewModel<AttendanceHistoryViewModel>()
            val uiState by viewModel.uiState.collectAsState()

            AttendanceHistoryScreen(attendanceHistoryUIState = uiState, viewModel::getHistories)
        }

        composable(route = BottomBarScreen.Profile.route)
        {
            val viewModel = hiltViewModel<ProfileViewModel>()
            val uiState by viewModel.uiState.collectAsState()

            ProfileScreen(navController, rootNavController, profileUIState = uiState, onLogoutPressed = viewModel::logout, viewModel::getCurrentUser)
        }
    }
}