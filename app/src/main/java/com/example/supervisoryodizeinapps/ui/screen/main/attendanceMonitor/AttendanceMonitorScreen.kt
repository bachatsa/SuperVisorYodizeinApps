package com.ydzmobile.supervisor.ui.screen.main.attendanceMonitor

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.supervisoryodizeinapps.R
import com.example.supervisoryodizeinapps.core.extension.dateStringToLong
import com.example.supervisoryodizeinapps.core.extension.longToDateStr
import com.example.supervisoryodizeinapps.core.viewModel.AttendanceMonitorUIState
import com.example.supervisoryodizeinapps.ui.component.atom.button.YMDateProfileDatePicker
import com.ydzmobile.supervisor.ui.component.molecule.main.attendanceMonitor.AttendanceMonitorCell
import com.ydzmobile.supervisor.ui.theme.littleBoyBlue
import com.ydzmobile.supervisor.ui.theme.poppinsFont

@Composable
fun AttendanceMonitorScreen(
    navController: NavController,
    uiState: AttendanceMonitorUIState,
    onDateChanged: (String) -> Unit,
    onViewAppear: () -> Unit
) {

    var showDatePicker by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        onViewAppear()
        Log.d("DebugRecomposition",  uiState.attendances.toString())

    }
    Box(

    ) {
        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 28.dp),
            verticalArrangement = Arrangement
                .spacedBy(14.dp)
        ) {
            item {
                Text(
                    text = stringResource(id = R.string.attendance_monitor_subtitle),
                    style = poppinsFont(size = 14, fontWeight = 500)
                )
            }

            item {
                Filter(Modifier.padding(bottom = 28.dp), uiState.selectedDate) {
                    showDatePicker = true
                }
            }

            items(
                items = uiState.attendances,
                key = {
                    it.id
                }
            ) {

                AttendanceMonitorCell(data = it)
                Log.d("Data Absence", it.toString())
            }
        }
        YMDateProfileDatePicker(
            showDatePicker = showDatePicker,
            selectedDate = uiState.selectedDate.dateStringToLong()
        ) { selectedDate ->
            showDatePicker = false
            if (selectedDate != null) {
                onDateChanged(selectedDate.longToDateStr())
            }
        }
    }
}

@Composable
private fun Filter(
    modifier: Modifier,
    selectedDate: String,
    onDatePickerPressed: () -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Spacer(modifier = Modifier.weight(1f))

        Button(
            modifier = Modifier
                .border(1.dp, littleBoyBlue, shape = RoundedCornerShape(8.dp))
                .height(28.dp)
                .padding(horizontal = 8.dp),
            onClick = onDatePickerPressed,
            shape = RoundedCornerShape(8.dp),
            contentPadding = PaddingValues(0.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White
            )
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Image(
                    modifier = Modifier.size(15.dp),
                    painter = painterResource(id = R.drawable.ic_calendar),
                    contentDescription = null,
                    contentScale = ContentScale.Fit
                )

                Text(
                    text = selectedDate,
                    style = poppinsFont(12, fontWeight = 400)
                )
            }
        }

        IconButton(onClick = { }) {
            Icon(
                modifier = Modifier.size(15.dp),
                painter = painterResource(id = R.drawable.ic_filter),
                contentDescription = null,
                tint = littleBoyBlue
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun AttendanceMonitorScreenPreview() {
    AttendanceMonitorScreen(rememberNavController(), AttendanceMonitorUIState(), {}, {})
}