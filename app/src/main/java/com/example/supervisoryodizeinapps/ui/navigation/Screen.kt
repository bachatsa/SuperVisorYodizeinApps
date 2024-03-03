package com.ydzmobile.supervisor.ui.navigation

const val ROOT_GRAPH_ROUTE = "root"
const val MAIN_GRAPH_ROUTE = "main"
const val AUTH_GRAPH_ROUTE = "auth"


const val TARGET_ID_ARGUMENT_KEY = "TARGET_ID_ARG_TARGET"
const val DIVISION_ID_ARGUMENT_KEY = "DIVISION_ID_ARG_TARGET"
const val DIVISION_ARGUMENT_KEY = "DIVISION_ARG_TARGET"
const val TARGET_ARGUMENT_KEY = "TARGET_ARGUMENT_KEY"

sealed class Screen (
    val route: String
) {
    object Main : Screen("main_route")
    object Login : Screen("login_route")
    object ForgotPassword : Screen("forgot_password_route")
    object ChangePassword : Screen("change_password_route")
    object Register : Screen("register_route")
    object Home : Screen("Monitoring Karyawan")
    object History : Screen("Riwayat")
    object AttendanceMonitor : Screen("Monitoring Absen")
    object DetailDivision :
        Screen("Detail_Division/{${DIVISION_ARGUMENT_KEY}}/{${DIVISION_ID_ARGUMENT_KEY}}")

    object UpdateTarget : Screen("Update_Target/{${TARGET_ARGUMENT_KEY}}")
    object CreateTarget : Screen("Crate_Target/{${DIVISION_ID_ARGUMENT_KEY}}")
    object Profile : Screen("Profile")

    object Attendance : Screen("attendance_route")

    object Permit : Screen("permit_route") {
        object Other : Screen("permit_other_route")
        object Sick : Screen("permit_sick_route")
    }

    object UpdateProfile : Screen("update_profile_route")
    object UpdatePasswordComplete : Screen("update_password_complete_route")
}