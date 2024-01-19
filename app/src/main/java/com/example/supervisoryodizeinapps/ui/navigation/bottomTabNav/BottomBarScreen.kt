package com.ydzmobile.supervisor.ui.navigation.bottomTabNav

import com.example.supervisoryodizeinapps.R
import com.ydzmobile.supervisor.ui.navigation.Screen

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: Int,
) {
    object Home: BottomBarScreen(
        route = Screen.Home.route,
        title = Screen.Home.route,
        icon = R.drawable.ic_home
    )

    object DetailDivision: BottomBarScreen(
        route = Screen.DetailDivision.route,
        title = Screen.DetailDivision.route,
        icon = R.drawable.ic_history
    )

    object UpdateTarget: BottomBarScreen(
        route = Screen.UpdateTarget.route,
        title = Screen.UpdateTarget.route,
        icon = R.drawable.ic_history
    )

    object CreateTarget: BottomBarScreen(
        route = Screen.CreateTarget.route,
        title = Screen.CreateTarget.route,
        icon = R.drawable.ic_history
    )

    object History: BottomBarScreen(
        route = Screen.History.route,
        title = Screen.History.route,
        icon = R.drawable.ic_history
    )

    object AttendanceMonitor: BottomBarScreen(
        route = Screen.AttendanceMonitor.route,
        title = Screen.AttendanceMonitor.route,
        icon = R.drawable.ic_monitor_attendance
    )

    object Profile: BottomBarScreen(
        route = Screen.Profile.route,
        title = Screen.Profile.route,
        icon = R.drawable.ic_profile
    )

}