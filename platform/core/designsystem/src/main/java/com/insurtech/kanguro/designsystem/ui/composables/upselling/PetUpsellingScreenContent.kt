package com.insurtech.kanguro.designsystem.ui.composables.upselling

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.BottomGradientAlpha5
import com.insurtech.kanguro.designsystem.ui.theme.BKLHeading3

@Composable
fun PetUpsellingScreenContent(
    modifier: Modifier,
    onTellMeMorePressed: () -> Unit
) {
    Box {
        Column(
            modifier = modifier
                .padding(horizontal = 24.dp)
                .padding(top = 16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.pet_upselling_pet_health_plan),
                style = BKLHeading3
            )

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = modifier
                    .padding(top = 16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                UpsellingContent(
                    imageId = R.drawable.img_pet_upselling_main,
                    titleId = R.string.do_you_have_pet,
                    upperTextId = R.string.pet_upselling_content_upper_text,
                    upperTextHighlightId = R.string.pet_upselling_content_upper_text_highlighted,
                    bottomTextId = R.string.pet_upselling_content_bottom_text,
                    bottomTextHighlightId = R.string.pet_upselling_content_bottom_text_highlighted
                )

                Spacer(modifier = Modifier.height(40.dp))

                TellMeMoreButton(
                    modifier = Modifier.padding(bottom = 16.dp),
                    onTellMeMorePressed = onTellMeMorePressed
                )
            }
        }

        BottomGradientAlpha5(
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
@Preview(device = "id:pixel_4_xl", showSystemUi = true, showBackground = true)
private fun PetUpsellingScreenContentUpsellingScreenContentPreview() {
    PetUpsellingScreenContent(
        modifier = Modifier,
        onTellMeMorePressed = {}
    )
}

@Composable
@Preview(device = "id:pixel_4_xl", showSystemUi = true, showBackground = true, locale = "es")
private fun PetUpsellingScreenContentUpsellingScreenContentLocalizedPreview() {
    PetUpsellingScreenContent(
        modifier = Modifier,
        onTellMeMorePressed = {}
    )
}
