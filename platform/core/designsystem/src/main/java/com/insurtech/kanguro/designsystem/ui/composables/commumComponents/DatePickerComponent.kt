package com.insurtech.kanguro.designsystem.ui.composables.commumComponents

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.toStringDate
import com.insurtech.kanguro.designsystem.ui.theme.MobaBodyRegular
import com.insurtech.kanguro.designsystem.ui.theme.MobaCaptionRegular
import com.insurtech.kanguro.designsystem.ui.theme.NeutralLightest
import com.insurtech.kanguro.designsystem.ui.theme.SecondaryDark
import com.insurtech.kanguro.designsystem.ui.theme.SecondaryMedium
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerComponent(
    modifier: Modifier = Modifier,
    onDateChange: (Date) -> Unit,
    title: @Composable () -> Unit
) {
    var selectedDateString by remember { mutableStateOf("") }
    var showDatePickerDialog by remember { mutableStateOf(false) }

    val datePickerState = rememberDatePickerState(
        initialDisplayMode = DisplayMode.Input
    )

    if (showDatePickerDialog) {
        KanguroDatePicker(
            datePickerState = datePickerState,
            showDatePickerDialog = { showDatePickerDialog = it },
            onDateChange = { millis ->
                selectedDateString = millis.toStringDate()
                onDateChange(Date(millis))
            }
        )
    }

    Column(
        modifier = modifier
    ) {
        title()
        Spacer(modifier = Modifier.height(8.dp))
        ContentTextField(
            selectedDateString = selectedDateString,
            onFieldPressed = {
                showDatePickerDialog = true
            }
        )
    }
}

@Composable
private fun ContentTextField(
    selectedDateString: String,
    onFieldPressed: () -> Unit
) {
    OutlinedButton(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(16.dp),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(width = 1.dp, color = SecondaryMedium),
        onClick = onFieldPressed
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = selectedDateString.ifEmpty { stringResource(id = R.string.select_date) },
            style = if (selectedDateString.isEmpty()) {
                MobaBodyRegular.copy(color = NeutralLightest)
            } else {
                MobaBodyRegular
            }
        )
    }
}

@Composable
@Preview
fun DatePickerComponentPreview() {
    Surface {
        DatePickerComponent(
            modifier = Modifier.padding(16.dp),
            onDateChange = {}
        ) {
            Text(
                text = stringResource(id = R.string.date_of_service_label),
                style = MobaCaptionRegular.copy(color = SecondaryDark)
            )
        }
    }
}
