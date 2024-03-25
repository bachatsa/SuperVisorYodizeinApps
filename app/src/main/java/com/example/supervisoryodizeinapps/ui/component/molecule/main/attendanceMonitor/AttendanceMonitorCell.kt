package com.ydzmobile.supervisor.ui.component.molecule.main.attendanceMonitor

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.supervisoryodizeinapps.core.domain.model.attendanceMonitor.AttendanceMonitorCellModel
import com.ydzmobile.supervisor.ui.theme.appleGreen
import com.ydzmobile.supervisor.ui.theme.lightCarminePink
import com.ydzmobile.supervisor.ui.theme.poppinsFont

@Composable
fun AttendanceMonitorCell(
    data: AttendanceMonitorCellModel
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                AnimatedVisibility(visible = !data.division.equals("-")) {
                    Column {
                Text(
                    text = data.userName,
                    style = poppinsFont(size = 14, fontWeight = 600)
                )

                Text(
                    text = "Peran: ${ data.division }",
                    style = poppinsFont(size = 12, fontWeight = 600)
                )
                val reasonToPrint = data.reasonOfPermission ?: ""


                AnimatedVisibility(visible = reasonToPrint.isNotEmpty()) {
                   Text(text = "Reason: ${reasonToPrint}",
                   style = poppinsFont(size = 12, fontWeight = 600),
                      )
                }
                    }
                }
            }
                Spacer(modifier = Modifier.weight(1f))
            AnimatedVisibility(visible = !data.division.equals("-")) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(if (data.attendanceType.lowercase() == "masuk") appleGreen else lightCarminePink)
                        .padding(vertical = 4.dp)
                        .width(90.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = data.attendanceType,
                        style = poppinsFont(size = 12, fontWeight = 700, color = Color.White)
                    )
                }
            }

        }

        Divider()
    }
}

@Preview(showSystemUi = true)
@Composable
private fun AttendanceMonitorCellPreview() {
    AttendanceMonitorCell(AttendanceMonitorCellModel("ID", "name", "division", "Masuk", "date", "reasonOfPermission",""))
}