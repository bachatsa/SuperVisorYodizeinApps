package com.ydzmobile.supervisor.ui.component.molecule.main.target

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.supervisoryodizeinapps.R
import com.example.supervisoryodizeinapps.core.domain.model.monitor.TargetModelCell
import com.example.supervisoryodizeinapps.ui.component.atom.text.YMTextWithDetail
import com.ydzmobile.supervisor.ui.theme.littleBoyBlue
import com.ydzmobile.supervisor.ui.theme.poppinsFont
import com.ydzmobile.supervisor.ui.theme.tuftsBlue

@Composable
fun TargetDetailCell(
    data: TargetModelCell,
    modifier: Modifier,
    onEditPressed: () -> Unit
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(littleBoyBlue)
            .padding(16.dp)
    ) {
        YMTextWithDetail(
            title = stringResource(id = R.string.targetList_card_employee_id),
            value = data.target.idEmployee,
            titleStyle = poppinsFont(size = 14, fontWeight = 700, color = Color.White),
            valueStyle = poppinsFont(size = 14, fontWeight = 700, color = Color.White),
        )
        YMTextWithDetail(
            title = stringResource(id = R.string.targetList_card_name),
            value = data.name,
            titleStyle = poppinsFont(size = 14, fontWeight = 700, color = Color.White),
            valueStyle = poppinsFont(size = 14, color = Color.White),
        )
        YMTextWithDetail(
            title = stringResource(id = R.string.targetList_card_total_target_done),
            value = data.target.targetBeenDone.toString(),
            titleStyle = poppinsFont(size = 14, fontWeight = 700, color = Color.White),
            valueStyle = poppinsFont(size = 14, color = Color.White),
        )
        YMTextWithDetail(
            title = stringResource(id = R.string.targetList_card_total_target),
            value = data.target.totalTarget.toString(),
            titleStyle = poppinsFont(size = 14, fontWeight = 700, color = Color.White),
            valueStyle = poppinsFont(size = 14, color = Color.White),
        )
        YMTextWithDetail(
            title = stringResource(id = R.string.targetList_card_product_type),
            value = data.target.productType,
            titleStyle = poppinsFont(size = 14, fontWeight = 700, color = Color.White),
            valueStyle = poppinsFont(size = 14, color = Color.White),
        )

        YMTextWithDetail(
            title = stringResource(id = R.string.target_type) + " :",
            value = data.target.targetType,
            titleStyle = poppinsFont(size = 14, fontWeight = 700, color = Color.White),
            valueStyle = poppinsFont(size = 14, color = Color.White),
        )
        YMTextWithDetail(
            title = stringResource(id = R.string.targetList_card_date_start),
            value = data.target.dateStart,
            titleStyle = poppinsFont(size = 14, fontWeight = 700, color = Color.White),
            valueStyle = poppinsFont(size = 14, color = Color.White),
        )

        YMTextWithDetail(
            title = stringResource(id = R.string.targetList_card_deadline),
            value = data.target.dateFinish,
            titleStyle = poppinsFont(size = 14, fontWeight = 700, color = Color.White),
            valueStyle = poppinsFont(size = 14, color = Color.White),
        )

        Row(
            modifier = Modifier.padding(top = 20.dp)
        ) {
            Spacer(modifier = Modifier.weight(1f))

            Button(
                modifier = Modifier
                    .height(28.dp)
                    .width(110.dp),
                onClick = onEditPressed,
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White
                )
            ) {
                Text(
                    text = stringResource(id = R.string.edit),
                    style = poppinsFont(size = 14, fontWeight = 700, color = tuftsBlue)
                )
            }
        }
    }
}