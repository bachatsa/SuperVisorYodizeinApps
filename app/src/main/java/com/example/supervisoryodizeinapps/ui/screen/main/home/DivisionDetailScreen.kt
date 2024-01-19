package com.ydzmobile.supervisor.ui.screen.main.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ydzmobile.supervisor.core.domain.model.monitor.toUriString
import com.ydzmobile.supervisor.core.viewModel.DivisionDetailUIState
import com.ydzmobile.supervisor.ui.component.molecule.main.target.TargetDetailCell
import com.ydzmobile.supervisor.ui.component.molecule.main.target.TargetNavbar
import com.ydzmobile.supervisor.ui.navigation.DIVISION_ID_ARGUMENT_KEY
import com.ydzmobile.supervisor.ui.navigation.TARGET_ID_ARGUMENT_KEY

@Composable
fun DivisionDetailScreen(
    navController: NavController,
    divisionName: String,
    divisionID: String,
    uiState: DivisionDetailUIState
) {
    Column {
        TargetNavbar(
            title = divisionName
        ) {
            navController.popBackStack()
        }

        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 28.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            items(
                items = uiState.targets,
                key = {
                    it.target.id
                }
            ) {
                TargetDetailCell(it, Modifier) {
                    navController.navigate("Update_Target/${it.target.toUriString()}")
                }
            }

            item {
                Spacer(modifier = Modifier.height(100.dp))
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun DivisionDetailScreenPreview() {
    DivisionDetailScreen(rememberNavController(), "adada", "", DivisionDetailUIState())
}