package com.insurtech.kanguro.designsystem.ui.composables.commumComponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.ui.composables.directPayToVet.domain.SelectedFileType
import com.insurtech.kanguro.designsystem.ui.composables.directPayToVet.domain.SelectedFileUi
import com.insurtech.kanguro.designsystem.ui.theme.MobaCaptionRegular
import com.insurtech.kanguro.designsystem.ui.theme.NeutralBackground
import com.insurtech.kanguro.designsystem.ui.theme.NeutralMedium2

@Composable
fun SelectedFileCard(
    modifier: Modifier = Modifier,
    selectedFileUi: SelectedFileUi,
    onDeletedPressed: () -> Unit
) {
    Surface(
        modifier = modifier.clip(RoundedCornerShape(4.dp)),
        color = NeutralBackground
    ) {
        Row(
            modifier = Modifier.padding(start = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                if (selectedFileUi.type == SelectedFileType.Image) {
                    SubcomposeAsyncImage(
                        modifier = Modifier
                            .size(30.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        model = selectedFileUi.fileUrl,
                        loading = {
                            ScreenLoader(
                                modifier = Modifier
                                    .size(30.dp)
                                    .padding(8.dp),
                                color = NeutralMedium2
                            )
                        },
                        error = {
                            Image(
                                painter = painterResource(id = R.drawable.ic_image_place_holder),
                                contentDescription = null
                            )
                        },
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Image(
                        painter = painterResource(id = selectedFileUi.type?.icon ?: R.drawable.ic_document_text),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(color = NeutralMedium2)
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = selectedFileUi.fileName ?: "",
                    style = MobaCaptionRegular.copy(color = NeutralMedium2)
                )
            }

            IconButton(
                onClick = onDeletedPressed
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_document_delete),
                    contentDescription = stringResource(id = R.string.close)
                )
            }
        }
    }
}

@Composable
@Preview
private fun SelectedDocumentCardPreview() {
    Surface {
        SelectedFileCard(
            modifier = Modifier.padding(16.dp),
            selectedFileUi = SelectedFileUi(
                id = 1,
                fileName = "238671236.jpg",
                fileUrl = "test",
                type = SelectedFileType.Image
            ),
            onDeletedPressed = {}
        )
    }
}

@Composable
@Preview
private fun SelectedDocumentCardFilePreview() {
    Surface {
        SelectedFileCard(
            modifier = Modifier.padding(16.dp),
            selectedFileUi = SelectedFileUi(
                id = 1,
                fileName = "document_sent.pdf",
                fileUrl = "test",
                type = SelectedFileType.File
            ),
            onDeletedPressed = {}
        )
    }
}

@Composable
@Preview
private fun SelectedDocumentCardFileLongNamePreview() {
    Surface {
        SelectedFileCard(
            modifier = Modifier.padding(16.dp),
            selectedFileUi = SelectedFileUi(
                id = 1,
                fileName = "document_assigned_with_vet_name_and_extra_informations.pdf",
                fileUrl = "test",
                type = SelectedFileType.File
            ),
            onDeletedPressed = {}
        )
    }
}
