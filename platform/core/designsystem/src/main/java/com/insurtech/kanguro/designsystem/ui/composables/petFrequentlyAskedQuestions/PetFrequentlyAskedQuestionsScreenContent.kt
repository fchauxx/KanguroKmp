package com.insurtech.kanguro.designsystem.ui.composables.petFrequentlyAskedQuestions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.ExpandableCard
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.KanguroMotionLayoutContainer
import com.insurtech.kanguro.designsystem.ui.composables.faq.model.QuestionModel
import com.insurtech.kanguro.designsystem.ui.composables.petFrequentlyAskedQuestions.model.PetFaqModel
import com.insurtech.kanguro.designsystem.ui.composables.petFrequentlyAskedQuestions.model.PetFrequentlyAskedQuestionsEvent
import com.insurtech.kanguro.designsystem.ui.theme.MobaHeadline
import com.insurtech.kanguro.designsystem.ui.theme.MobaSubheadBold
import com.insurtech.kanguro.designsystem.ui.theme.MobaSubheadRegular
import com.insurtech.kanguro.designsystem.ui.theme.PrimaryDarkest
import com.insurtech.kanguro.designsystem.ui.theme.SecondaryMedium
import com.insurtech.kanguro.designsystem.ui.theme.White

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PetFrequentlyAskedQuestionsScreenContent(
    model: PetFaqModel,
    isLoading: Boolean,
    isError: Boolean,
    onEvent: (PetFrequentlyAskedQuestionsEvent) -> Unit
) {
    val pullRefreshState = rememberPullRefreshState(
        refreshing = false,
        onRefresh = {
            onEvent(PetFrequentlyAskedQuestionsEvent.OnPullToRefresh)
        }
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = White)
            .pullRefresh(pullRefreshState)
    ) {
        Content(
            model = model,
            isLoading = isLoading,
            isError = isError,
            onBackPressed = {
                onEvent(PetFrequentlyAskedQuestionsEvent.OnBackPressed)
            },
            onTryAgainPressed = {
                onEvent(PetFrequentlyAskedQuestionsEvent.OnTryAgainPressed)
            }
        )

        PullRefreshIndicator(
            refreshing = false,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}

@Composable
private fun Content(
    model: PetFaqModel,
    isLoading: Boolean,
    isError: Boolean,
    onBackPressed: () -> Unit,
    onTryAgainPressed: () -> Unit
) {
    KanguroMotionLayoutContainer(
        image = R.drawable.img_pet_faq_banner,
        isLoading = isLoading,
        isError = isError,
        onBackPressed = onBackPressed,
        onTryAgainPressed = onTryAgainPressed
    ) {
        Column {
            Text(
                text = stringResource(id = R.string.faq),
                style = MobaHeadline.copy(color = PrimaryDarkest)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = stringResource(id = R.string.frequently_asked_questions),
                style = MobaSubheadBold
            )

            Spacer(modifier = Modifier.height(32.dp))

            Faq(model.petFaq)

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
private fun Faq(questions: List<QuestionModel>) {
    val currentlyExpanded = remember { mutableStateOf<Int?>(null) }

    Column(
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        questions.forEachIndexed { index, item ->
            ExpandableCard(
                title = item.question,
                isExpanded = currentlyExpanded.value == index,
                onClick = {
                    currentlyExpanded.value = if (currentlyExpanded.value == index) null else index
                }
            ) {
                Text(
                    modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
                    text = item.answer,
                    style = MobaSubheadRegular.copy(color = SecondaryMedium)
                )
            }
        }
    }
}

@Composable
@Preview
private fun PetFrequentlyAskedQuestionsScreenContentPreview() {
    PetFrequentlyAskedQuestionsScreenContent(
        model =
        PetFaqModel(
            petFaq = listOf(
                QuestionModel(
                    question = "Lorem ipsum",
                    answer = "Lorem ipsum Lorem ipsum Lorem ipsum"
                ),
                QuestionModel(
                    question = "Lorem ipsum",
                    answer = "Lorem ipsum Lorem ipsum Lorem ipsum"
                ),
                QuestionModel(
                    question = "Lorem ipsum",
                    answer = "Lorem ipsum Lorem ipsum Lorem ipsum"
                )
            )

        ),
        isLoading = false,
        isError = false,
        onEvent = {}
    )
}

@Composable
@Preview
private fun PetFrequentlyAskedQuestionsScreenContentLoadingPreview() {
    PetFrequentlyAskedQuestionsScreenContent(
        model = PetFaqModel(listOf()),
        isLoading = true,
        isError = false,
        onEvent = {}
    )
}

@Composable
@Preview
private fun PetFrequentlyAskedQuestionsScreenContentErrorPreview() {
    PetFrequentlyAskedQuestionsScreenContent(
        model = PetFaqModel(listOf()),
        isLoading = false,
        isError = true,
        onEvent = {}
    )
}
