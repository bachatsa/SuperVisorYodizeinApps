package com.ydzmobile.supervisor.ui.component.molecule.main.target

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.supervisoryodizeinapps.R
import com.ydzmobile.supervisor.ui.theme.darkJungleGreen
import com.ydzmobile.supervisor.ui.theme.poppinsFont

@Composable
fun TargetNavbar(
    title: String,
    foregroundColor: Color = darkJungleGreen,
    onBackPressed: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically
    ){

        IconButton(onClick = onBackPressed) {
            Icon(
                modifier = Modifier
                    .size(18.dp),
                painter = painterResource(id = R.drawable.ic_chevron_left),
                contentDescription = null,
                tint = foregroundColor
            )
        }

        Text(
            text = title,
            style = poppinsFont(size = 26, fontWeight = 700)
        )
        Spacer(modifier = Modifier.weight(1f))

    }
}