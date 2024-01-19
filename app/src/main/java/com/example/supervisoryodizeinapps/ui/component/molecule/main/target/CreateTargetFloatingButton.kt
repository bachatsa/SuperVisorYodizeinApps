package com.ydzmobile.supervisor.ui.component.molecule.main.target

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.supervisoryodizeinapps.R
import com.ydzmobile.supervisor.ui.theme.poppinsFont
import com.ydzmobile.supervisor.ui.theme.tealBlue

@Composable
fun CreateTargetFloatingButton(
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(bottom = 52.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            modifier = Modifier
                .size(66.dp),
            onClick = onClick,
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(
                containerColor = tealBlue
            ),
            contentPadding = PaddingValues(0.dp)
        ) {
            Icon(
                modifier = Modifier.size(28.dp),
                painter = painterResource(id = R.drawable.ic_add),
                contentDescription = null,
                tint = Color.White
            )
        }

        Text(
            text = stringResource(id = R.string.add),
            style = poppinsFont(size = 20, fontWeight = 700, color = tealBlue)
        )
    }
}