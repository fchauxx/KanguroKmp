package com.insurtech.kanguro.designsystem.ui.composables.commumComponents

import androidx.annotation.DrawableRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.domain.CoverageStatusUi
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.domain.PetType
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.domain.PetsCoverageSummaryCardModel
import com.insurtech.kanguro.designsystem.ui.theme.ClickableTextStyleNormal
import com.insurtech.kanguro.designsystem.ui.theme.LatoBoldNeutralMedium2Size10
import com.insurtech.kanguro.designsystem.ui.theme.LatoRegularSecondaryDarkSize12
import com.insurtech.kanguro.designsystem.ui.theme.MobaHeadline
import com.insurtech.kanguro.designsystem.ui.theme.NeutralBackground
import com.insurtech.kanguro.designsystem.ui.theme.PrimaryDarkest
import com.insurtech.kanguro.designsystem.ui.theme.petCarouselCardHeight
import java.util.Calendar

@Composable
fun PetsCoverageSummaryCard(
    modifier: Modifier = Modifier,
    coverage: PetsCoverageSummaryCardModel,
    onPressed: () -> Unit
) {
    CoverageSummaryCardContainer(
        modifier = modifier.height(petCarouselCardHeight).width(157.dp),
        onPressed = onPressed
    ) {
        Column {
            HeaderPicture(
                pictureUrl = coverage.pictureUrl,
                placeHolder = coverage.petType.placeHolder
            )

            Spacer(modifier = Modifier.height(16.dp))

            PetDetails(coverage = coverage) {
                onPressed()
            }
        }
    }
}

@Composable
private fun PetDetails(
    coverage: PetsCoverageSummaryCardModel,
    onPressed: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(bottom = 16.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = coverage.name,
            style = MobaHeadline.copy(color = PrimaryDarkest),
            overflow = TextOverflow.Ellipsis,
            maxLines = 2
        )

        BreedAndAge(
            modifier = Modifier
                .padding(top = 4.dp),
            breed = coverage.breed,
            age = coverage.getAge()
        )

        PolicyStatus(
            modifier = Modifier
                .padding(top = 4.dp),
            status = coverage.status
        )

        Spacer(modifier = Modifier.weight(1f))

        SeeDetails(
            modifier = Modifier.padding(top = 12.dp),
            onPressed = onPressed
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun BreedAndAge(
    modifier: Modifier = Modifier,
    breed: String,
    age: Int?
) {
    val concatenatedString = if (age != null) {
        "$breed, $age${stringResource(id = R.string.years_prefix)}"
    } else {
        breed
    }

    Text(
        modifier = modifier.basicMarquee(),
        text = concatenatedString,
        style = LatoRegularSecondaryDarkSize12,
        maxLines = 1
    )
}

@Composable
private fun SeeDetails(
    modifier: Modifier = Modifier,
    onPressed: () -> Unit
) {
    ClickableText(
        modifier = modifier,
        style = ClickableTextStyleNormal,
        text = buildAnnotatedString {
            append(stringResource(id = R.string.see_details))
        },
        onClick = { onPressed() }
    )
}

@Composable
private fun PolicyStatus(
    modifier: Modifier = Modifier,
    status: CoverageStatusUi
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Text(
            text = stringResource(id = R.string.middle_dot),
            style = LatoRegularSecondaryDarkSize12.copy(color = status.color)
        )

        Text(
            text = stringResource(id = status.label),
            style = LatoBoldNeutralMedium2Size10.copy(fontWeight = FontWeight.W400)
        )
    }
}

@Composable
private fun HeaderPicture(
    pictureUrl: String,
    @DrawableRes placeHolder: Int
) {
    Box(
        modifier = Modifier.height(86.dp)
    ) {
        SubcomposeAsyncImage(
            model = pictureUrl,
            loading = {
                ScreenLoader(
                    modifier = Modifier
                        .padding(start = 16.dp, top = 16.dp, end = 16.dp)
                        .fillMaxWidth()
                )
            },
            error = {
                Image(
                    painter = painterResource(id = placeHolder),
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            },
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

@Preview
@Composable
private fun PetsCoverageSummaryCardModelPreview() {
    Surface(
        color = NeutralBackground
    ) {
        PetsCoverageSummaryCard(
            coverage = PetsCoverageSummaryCardModel(
                id = "1",
                breed = "Labradoodle",
                birthDate = Calendar.getInstance().apply {
                    set(Calendar.YEAR, 2019)
                    set(Calendar.MONTH, Calendar.NOVEMBER)
                    set(Calendar.DAY_OF_MONTH, 29)
                }.time,
                status = CoverageStatusUi.Active,
                pictureUrl = "",
                name = "Oliver",
                petType = PetType.Dog
            ),
            onPressed = {},
            modifier = Modifier
                .padding(16.dp)
        )
    }
}

@Preview
@Composable
private fun PetsCoverageSummaryCardModelBigNamePreview() {
    Surface(
        color = NeutralBackground
    ) {
        PetsCoverageSummaryCard(
            coverage = PetsCoverageSummaryCardModel(
                id = "1",
                breed = "Labradoodle",
                birthDate = Calendar.getInstance().apply {
                    set(Calendar.YEAR, 2019)
                    set(Calendar.MONTH, Calendar.NOVEMBER)
                    set(Calendar.DAY_OF_MONTH, 29)
                }.time,
                status = CoverageStatusUi.Active,
                pictureUrl = "",
                name = "A Big Name For a Pet to Have",
                petType = PetType.Dog
            ),
            onPressed = {},
            modifier = Modifier
                .padding(16.dp)
        )
    }
}
