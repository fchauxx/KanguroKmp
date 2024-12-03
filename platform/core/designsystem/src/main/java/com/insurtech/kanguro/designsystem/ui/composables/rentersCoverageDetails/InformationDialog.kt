package com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.ui.theme.BKSParagraphBlack
import com.insurtech.kanguro.designsystem.ui.theme.BKSParagraphRegular
import com.insurtech.kanguro.designsystem.ui.theme.NeutralBackground
import com.insurtech.kanguro.designsystem.ui.theme.PrimaryDark

@Composable
fun InformationDialog(
    title: String,
    content: @Composable () -> Unit,
    onDismiss: () -> Unit = {}
) {
    Box(
        modifier = Modifier
    ) {
        Dialog(onDismissRequest = { onDismiss() }) {
            CardBox {
                CloseButton { onDismiss() }
                DialogContent(title, content)
            }
        }
    }
}

@Composable
private fun CardBox(content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        backgroundColor = NeutralBackground
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 32.dp)
                .padding(top = 24.dp, bottom = 32.dp)
        ) {
            content()
        }
    }
}

@Composable
private fun CloseButton(onClick: () -> Unit) {
    Row {
        Spacer(Modifier.weight(1f))
        Image(
            modifier = Modifier.clickable { onClick() },
            painter = painterResource(id = R.drawable.ic_information_close),
            contentDescription = stringResource(id = R.string.close)
        )
    }
}

@Composable
private fun DialogContent(title: String, content: @Composable () -> Unit) {
    Text(text = title, style = BKSParagraphBlack)
    Box(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .width(33.dp)
            .height(4.dp)
            .background(
                color = PrimaryDark,
                shape = RoundedCornerShape(8.dp)
            )
    )
    content()
}

@Composable
@Preview
private fun InformationDialogPreview() {
    Surface {
        InformationDialog(
            title = "Replacement Cost",
            content = {
                Text(
                    text = "Water Sewer Backup Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla id turpis lectus. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla id turpis lectus.",
                    style = BKSParagraphRegular
                )
            }
        )
    }
}
