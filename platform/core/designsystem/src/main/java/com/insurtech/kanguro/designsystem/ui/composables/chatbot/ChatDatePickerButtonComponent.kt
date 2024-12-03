package com.insurtech.kanguro.designsystem.ui.composables.chatbot

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.toStringDate
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.KanguroDatePicker
import com.insurtech.kanguro.designsystem.ui.theme.MobaBodyRegular
import com.insurtech.kanguro.designsystem.ui.theme.NeutralBackground
import com.insurtech.kanguro.designsystem.ui.theme.NeutralLightest
import com.insurtech.kanguro.designsystem.ui.theme.SecondaryMedium
import com.insurtech.kanguro.designsystem.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun ChatDatePickerButtonComponent(
    modifier: Modifier = Modifier,
    onSubmit: (String) -> Unit
) {
    var selectedDate by remember { mutableStateOf("") }
    var showDatePickerDialog by remember { mutableStateOf(false) }

    val datePickerState = rememberDatePickerState(
        initialDisplayMode = DisplayMode.Input
    )

    if (showDatePickerDialog) {
        KanguroDatePicker(
            datePickerState = datePickerState,
            showDatePickerDialog = { showDatePickerDialog = it },
            onDateChange = { millis ->
                selectedDate = millis.toStringDate()
            }
        )
    }

    Box(
        modifier = modifier
            .background(color = NeutralBackground)
            .padding(vertical = 8.dp, horizontal = 16.dp)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    border = BorderStroke(1.dp, NeutralLightest),
                    shape = RoundedCornerShape(16.dp)
                )
                .clip(shape = RoundedCornerShape(16.dp))
                .background(color = NeutralBackground)
                .height(36.dp),
            onClick = { showDatePickerDialog = true }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = White, shape = RoundedCornerShape(16.dp))
                    .clip(shape = RoundedCornerShape(16.dp))
                    .padding(vertical = 4.dp)
                    .padding(start = 16.dp, end = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if (selectedDate.isEmpty()) {
                    Text(
                        text = stringResource(id = R.string.date_picker_hint),
                        style = MobaBodyRegular.copy(color = NeutralLightest)
                    )
                } else {
                    Text(
                        text = selectedDate,
                        style = MobaBodyRegular
                    )
                }

                IconButton(
                    modifier = Modifier
                        .size(26.dp)
                        .padding(start = 4.dp),
                    onClick = { onSubmit(selectedDate) },
                    enabled = selectedDate.isNotEmpty()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_send),
                        contentDescription = stringResource(
                            id = R.string.send
                        ),
                        colorFilter = ColorFilter.tint(color = if (selectedDate.isNotEmpty()) SecondaryMedium else NeutralLightest)
                    )
                }
            }
        }
    }
}

@Composable
@Preview
private fun ChatDatePickerComponentPreview() {
    Surface {
        ChatDatePickerButtonComponent(
            modifier = Modifier.padding(16.dp),
            onSubmit = {}
        )
    }
}
