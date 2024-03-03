package com.ydzmobile.supervisor.ui.screen.main.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.supervisoryodizeinapps.core.viewModel.HomeUIState
import com.ydzmobile.supervisor.ui.component.molecule.main.target.TargetCell
import com.ydzmobile.supervisor.ui.theme.tealBlue

@Composable
fun HomeScreen(
    navController: NavController,
    homeUIState: HomeUIState,
    onViewAppear: () -> Unit
) {
    LaunchedEffect(Unit) {
        onViewAppear()
    }
    Column(
        modifier = Modifier
            .padding(horizontal = 28.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Row(){
            Column(
                modifier = Modifier
                    .height(2.dp)
                    .weight(2f)
                    .background(tealBlue)
                    ,
                content = {}
            )

            Spacer(modifier = Modifier.weight(1f))
        }

        AnimatedVisibility(
            modifier = Modifier.padding(top = 16.dp),
            visible = homeUIState.divisionList.isNotEmpty()
        ){
            LazyColumn(
                modifier = Modifier
                    .heightIn(max = (66 * homeUIState.divisionList.count() + ((homeUIState.divisionList.count() - 1) * 30)).dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(
                    items = homeUIState.divisionList,
                    key = { data ->
                        data.name
                    },
                ) {
                    TargetCell(title = it.name) {
                        navController.navigate("Detail_Division/${it.name}/${it.id}")
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun HomeScreenPreview() {
    HomeScreen(rememberNavController(), homeUIState = HomeUIState(listOf()), {})
}