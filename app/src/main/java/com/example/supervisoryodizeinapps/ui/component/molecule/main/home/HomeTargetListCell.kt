package com.ydzmobile.supervisor.ui.component.molecule.main.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.supervisoryodizeinapps.R
import com.ydzmobile.supervisor.core.domain.model.TargetListCellModel
import com.ydzmobile.supervisor.ui.component.atom.text.YMTextWithDetail
import com.ydzmobile.supervisor.ui.theme.littleBoyBlue
import com.ydzmobile.supervisor.ui.theme.poppinsFont

@Composable
fun HomeTargetListCell(
    data: TargetListCellModel
) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .height(126.dp)
            .fillMaxWidth()
            .background(littleBoyBlue)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        YMTextWithDetail(title = stringResource(id = R.string.target_list_deadline), value = data.deadline)
        YMTextWithDetail(title = stringResource(id = R.string.target_list_code), value = data.targetCode)
        TargetDetail(data)
        YMTextWithDetail(title = stringResource(id = R.string.target_list_type), value = data.type)
    }
}

@Composable
private fun TargetDetail(
    data: TargetListCellModel
) {
    val targetValue = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                fontFamily = poppinsFont().fontFamily,
                fontWeight = FontWeight(400),
                fontSize = 16.sp,
                color = Color.White
            )
        ) {
            append(data.targetBeenDone.toString())
        }

        withStyle(
            style = SpanStyle(
                fontFamily = poppinsFont().fontFamily,
                fontWeight = FontWeight(700),
                fontSize = 16.sp,
                color = Color.White
            )
        ) {
            append(" /${data.target}")
        }
    }

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = stringResource(id = R.string.target_list_target), style = poppinsFont(size = 16, color = Color.White))

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = targetValue,
            textAlign = TextAlign.Start,
            maxLines = 1
        )
    }
}

//@Preview
//@Composable
//private fun HomeTargetListCellPreview() {
//    HomeTargetListCell()
//}