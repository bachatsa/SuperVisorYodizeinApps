package com.ydzmobile.supervisor.ui.navigation.graph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.google.firebase.auth.FirebaseAuth
import com.ydzmobile.supervisor.ui.navigation.*

@Composable
fun RootNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = AUTH_GRAPH_ROUTE,
//        startDestination = MAIN_GRAPH_ROUTE,
        route = ROOT_GRAPH_ROUTE,
    ) {
        mainScreenNavGraph(navController)
        authNavGraph(navController)
    }
}