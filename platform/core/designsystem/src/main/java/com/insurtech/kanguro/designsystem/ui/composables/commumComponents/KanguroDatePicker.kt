package com.insurtech.kanguro.designsystem.ui.composables.commumComponents

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.ui.theme.KanguroDatePickerTheme
import com.insurtech.kanguro.designsystem.ui.theme.NeutralBackground
import com.insurtech.kanguro.designsystem.ui.theme.SecondaryDarkest
import com.insurtech.kanguro.designsystem.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KanguroDatePicker(
    datePickerState: DatePickerState = rememberDatePickerState(initialDisplayMode = DisplayMode.Input),
    showDatePickerDialog: (Boolean) -> Unit = { },
    onDateChange: (Long) -> Unit
) {
    KanguroDatePickerTheme {
        DatePickerDialog(
            onDismissRequest = { showDatePickerDialog(false) },
            confirmButton = {
                Button(
                    colors = ButtonDefaults.buttonColors(backgroundColor = SecondaryDarkest),
                    modifier = Modifier.padding(end = 12.dp, bottom = 12.dp),
                    onClick = {
                        datePickerState.selectedDateMillis?.let { onDateChange(it) }
                        showDatePickerDialog(false)
                    }
                ) {
                    Text(text = stringResource(id = R.string.confirm), color = White)
                }
            },
            colors = DatePickerDefaults.colors(
                containerColor = NeutralBackground
            )
        ) {
            DatePicker(state = datePickerState)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(showBackground = true, device = "id:pixel_4_xl", showSystemUi = true)
private fun KanguroDatePickerPreview() {
    KanguroDatePicker(
        showDatePickerDialog = { },
        onDateChange = { }
    )
}
