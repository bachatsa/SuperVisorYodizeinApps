package com.example.supervisoryodizeinapps.ui.component.atom.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.supervisoryodizeinapps.R
import com.ydzmobile.supervisor.ui.theme.darkJungleGreen
import com.ydzmobile.supervisor.ui.theme.poppinsFont

@Composable
fun YMDropDown2(
    selectedDropDown: String,
    label: String,
    options: List<String>,
    onDropDownSelected: (String) -> Unit,
    modifier: Modifier
) {
    var isActive by remember { mutableStateOf(false) }
    val title = selectedDropDown
    Box(
        modifier = modifier
            .wrapContentSize(Alignment.TopStart)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = label,
                style = poppinsFont(size = 16, fontWeight = 700, color = Color.White),
            )

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White
                ),
                contentPadding = PaddingValues(horizontal = 14.dp),
                onClick = {
                    isActive = !isActive
                },
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {

                    Text(
                        text = title,
                        style = poppinsFont(size = 14, color = darkJungleGreen, fontWeight = 700)
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Icon(
                        modifier = Modifier.size(12.dp),
                        painter = painterResource(id = R.drawable.ic_chevron_down),
                        contentDescription = null,
                        tint = Color.Black
                    )
                }
            }
        }

        DropdownMenu(
            modifier = Modifier
                .width(330.dp)
                .background(Color.White.copy(0.8f)),
            expanded = isActive,
            onDismissRequest = { isActive = false }
        ) {
            options.forEach {
                DropdownMenuItem(
                    modifier = Modifier.background(Color.Transparent),
                    text = {
                        Text(
                            text = it,
                            style = poppinsFont(size = 11, fontWeight = 400)
                        )
                    },
                    onClick = {
                        onDropDownSelected(it)
                        isActive = !isActive
                    },
                )
            }
        }
    }
}

@Preview
@Composable
private fun YMDropDown2Preview() {
    YMDropDown2(
        selectedDropDown = "ada",
        label = "ada",
        options = listOf(
            "Ada", "ada"
        ),
        onDropDownSelected = {},
        modifier = Modifier
    )
}