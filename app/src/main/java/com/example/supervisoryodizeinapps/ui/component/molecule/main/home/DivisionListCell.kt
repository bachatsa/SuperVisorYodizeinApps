package com.ydzmobile.supervisor.ui.component.molecule.main.home

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.supervisoryodizeinapps.core.domain.model.division.Division

@Composable
fun DivisionListCell(
    division: Division,
    onEditPressed: () -> Unit
) {
    Column(

    ) {

    }
}

@Preview
@Composable
private fun DivisionListCellPreview() {
    DivisionListCell(
        Division()
    ) {

    }
}