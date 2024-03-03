package com.ydzmobile.supervisor.ui.screen.main.target

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.supervisoryodizeinapps.R
import com.example.supervisoryodizeinapps.core.domain.model.auth.User
import com.example.supervisoryodizeinapps.core.extension.longToDateStr
import com.example.supervisoryodizeinapps.core.viewModel.CreateUpdateUIState
import com.example.supervisoryodizeinapps.ui.component.atom.button.YMDateProfileDatePicker
import com.example.supervisoryodizeinapps.ui.component.atom.button.YMDropDown2
import com.example.supervisoryodizeinapps.ui.component.atom.button.YMDropDownUsers
import com.example.supervisoryodizeinapps.ui.component.atom.textfield.YMBasicTextField
import com.ydzmobile.supervisor.ui.component.molecule.main.target.TargetNavbar
import com.ydzmobile.supervisor.ui.theme.littleBoyBlue
import com.ydzmobile.supervisor.ui.theme.poppinsFont
import com.ydzmobile.supervisor.ui.theme.tuftsBlue

@Composable
fun CreateUpdateTargetScreen(
    navController: NavController,
    uiState: CreateUpdateUIState,
    onSelectedUser: (User) -> Unit,
    onTotalTarget: (String) -> Unit,
    onProductType: (String) -> Unit,
    onDateStart: (String) -> Unit,
    onDateEnd: (String) -> Unit,
    onTargetType: (String) -> Unit,
    onCreateTargetPressed: () -> Unit,
    onUpdateTargetPressed: () -> Unit,
    onDeleteTargetPressed: () -> Unit,
) {
    var showDatePicker by remember { mutableStateOf(false) }
    var isFirstDatePicker by remember { mutableStateOf(false) }
    LaunchedEffect(key1 = uiState.isSuccess) {
        if (uiState.isSuccess) {
            navController.popBackStack()
        }
    }

    Box(

    ) {
        Column() {
            TargetNavbar(
                title = if (uiState.isEditMode) stringResource(id = R.string.update_target) else stringResource(
                    id = R.string.add_target
                )
            ) {
                navController.popBackStack()
            }

            Column(
                modifier = Modifier
                    .padding(horizontal = 28.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(littleBoyBlue)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp, vertical = 32.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                YMDropDownUsers(
                    selectedDropDown = uiState.selectedUser,
                    label = stringResource(id = R.string.employee_id),
                    options = uiState.users,
                    onDropDownSelected = onSelectedUser,
                    modifier = Modifier,
                    enable = !uiState.isEditMode
                )

                YMBasicTextField(
                    title = stringResource(id = R.string.total_target),
                    hint = stringResource(id = R.string.total_target),
                    value = uiState.totalTarget,
                    onValueChange = onTotalTarget,
                    keyboardType = KeyboardType.Number
                )

                YMBasicTextField(
                    title = stringResource(id = R.string.product_type),
                    hint = stringResource(id = R.string.product_type),
                    value = uiState.productType,
                    onValueChange = onProductType
                )

                Surface(
                    onClick = {
                        showDatePicker = true
                        isFirstDatePicker = true
                    },
                    color = Color.Transparent
                ) {
                    YMBasicTextField(
                        title = stringResource(id = R.string.date_start),
                        hint = stringResource(id = R.string.date_start),
                        value = uiState.dateStart,
                        isEnable = false,
                        isKeepBackgroundColor = true,
                        onValueChange = onDateStart
                    )
                }

                Surface(
                    onClick = {
                        showDatePicker = true
                        isFirstDatePicker = false
                    },
                    color = Color.Transparent
                ) {
                    YMBasicTextField(
                        title = stringResource(id = R.string.deadline),
                        hint = stringResource(id = R.string.deadline),
                        value = uiState.dateEnd,
                        isEnable = false,
                        isKeepBackgroundColor = true,
                        onValueChange = onDateEnd
                    )
                }

                YMDropDown2(
                    selectedDropDown = uiState.targetType,
                    label = stringResource(id = R.string.target_type),
                    options = listOf(
                        "Primer", "Sekunder"
                    ),
                    onDropDownSelected = onTargetType,
                    modifier = Modifier
                )

                AnimatedContent(
                    modifier = Modifier.padding(top = 58.dp),
                    targetState = uiState.isEditMode, label = ""
                ) {
                    when (it) {
                        true -> {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Button(
                                    modifier = Modifier
                                        .height(28.dp)
                                        .width(110.dp),
                                    onClick = onDeleteTargetPressed,
                                    contentPadding = PaddingValues(0.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color.White
                                    ),
                                    enabled =  uiState.isHasDoAttendance,
                                ) {
                                    Text(
                                        text = stringResource(id = R.string.delete),
                                        style = poppinsFont(
                                            size = 14,
                                            fontWeight = 700,
                                            color = tuftsBlue
                                        )
                                    )
                                }

                                Button(
                                    modifier = Modifier
                                        .height(28.dp)
                                        .width(110.dp),
                                    onClick = onUpdateTargetPressed,
                                    contentPadding = PaddingValues(0.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color.White
                                    ),
                                    enabled = uiState.isValid && uiState.isHasDoAttendance,
                                ) {
                                    Text(
                                        text = stringResource(id = R.string.update),
                                        style = poppinsFont(
                                            size = 14,
                                            fontWeight = 700,
                                            color = tuftsBlue
                                        )
                                    )
                                }
                            }
                        }

                        else -> {
                            Button(
                                modifier = Modifier
                                    .height(28.dp)
                                    .width(110.dp),
                                onClick = onCreateTargetPressed,
                                contentPadding = PaddingValues(0.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.White
                                ),
                                enabled = uiState.isValid && uiState.isHasDoAttendance,
                            ) {
                                Text(
                                    text = stringResource(id = R.string.add),
                                    style = poppinsFont(
                                        size = 14,
                                        fontWeight = 700,
                                        color = tuftsBlue
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }

        YMDateProfileDatePicker(
            showDatePicker = showDatePicker,
            selectedDate = if (isFirstDatePicker) {
                uiState.dateStart.toLongOrNull() ?: System.currentTimeMillis()
            } else {
                uiState.dateEnd.toLongOrNull() ?: System.currentTimeMillis()
            }
        ) { selectedDate ->
            showDatePicker = false
            if (selectedDate != null) {
                if (isFirstDatePicker) {
                    onDateStart(selectedDate.longToDateStr("dd-MM-yyyy"))
                } else {
                    onDateEnd(selectedDate.longToDateStr("dd-MM-yyyy"))
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun CreateUpdateTargetScreenPreview() {
    CreateUpdateTargetScreen(
        rememberNavController(),
        CreateUpdateUIState(isEditMode = true),
        {}, {}, {}, {}, {}, {}, {}, {}, {}
    )
}