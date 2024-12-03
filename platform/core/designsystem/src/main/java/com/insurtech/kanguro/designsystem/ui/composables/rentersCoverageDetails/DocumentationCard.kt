package com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.ExpandableCardStateful
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.HorizontalDivider
import com.insurtech.kanguro.designsystem.ui.theme.MobaBodyRegular
import com.insurtech.kanguro.domain.model.PolicyDocument

@Composable
fun DocumentationCard(
    modifier: Modifier = Modifier,
    documents: List<PolicyDocument>,
    onDocumentPressed: (PolicyDocument) -> Unit = {}
) {
    ExpandableCardStateful(
        modifier = modifier,
        title = stringResource(id = R.string.documentation),
        icon = R.drawable.ic_document_favorite
    ) {
        Column(
            modifier = Modifier.padding(top = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            documents.forEach { document ->
                document.filename?.let {
                    DocumentItem(
                        name = it,
                        onPressed = { onDocumentPressed(document) }
                    )
                }
            }
        }
    }
}

@Composable
private fun DocumentItem(
    name: String,
    onPressed: () -> Unit = {}
) {
    Column {
        HorizontalDivider(modifier = Modifier.height(1.dp))
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .clickable { onPressed() },
            text = name,
            style = MobaBodyRegular
        )
    }
}

@Composable
@Preview
private fun DocumentationCardPreview() {
    Surface {
        DocumentationCard(
            modifier = Modifier.padding(16.dp),
            documents = listOf(
                PolicyDocument(id = 1, filename = "Declarations and Schedule"),
                PolicyDocument(id = 2, filename = "Policy Document"),
                PolicyDocument(id = 3, filename = "Policy Document - State"),
                PolicyDocument(id = 4, filename = "Preventive Care")
            )
        )
    }
}

@Composable
@Preview
private fun DocumentItemPreview() {
    Surface {
        DocumentItem(name = "Declarations and Schedule")
    }
}
