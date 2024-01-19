package com.ydzmobile.supervisor.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ydzmobile.supervisor.ui.navigation.graph.RootNavGraph
import com.ydzmobile.supervisor.ui.theme.YdzMobileSupervisorKotlinTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            YdzMobileSupervisorKotlinTheme {
                navController = rememberNavController()
                RootNavGraph(navController = navController)
            }
        }
    }
}