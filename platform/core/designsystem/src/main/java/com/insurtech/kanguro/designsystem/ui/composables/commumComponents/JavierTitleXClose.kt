package com.insurtech.kanguro.designsystem.ui.composables.commumComponents

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.R

@Composable
fun JavierTitleXClose(
    modifier: Modifier = Modifier,
    title: String,
    onClosePressed: () -> Unit
) {
    Column(
        modifier = modifier
    ) {
        RightButtonClose(
            onClosePressed = { onClosePressed() },
            modifier = Modifier
                .padding(
                    top = 8.dp,
                    end = 8.dp
                )
        )

        JavierHeader(
            title = title,
            modifier = Modifier
                .padding(
                    horizontal = 32.dp
                )
        )
    }
}

@Preview
@Composable
private fun JavierTitleXClosePreview() {
    Surface() {
        JavierTitleXClose(
            title = stringResource(id = R.string.scheduled_items_title),
            onClosePressed = {}
        )
    }
}
