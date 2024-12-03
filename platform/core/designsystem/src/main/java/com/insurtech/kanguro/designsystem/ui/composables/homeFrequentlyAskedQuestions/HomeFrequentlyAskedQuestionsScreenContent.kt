package com.insurtech.kanguro.designsystem.ui.composables.homeFrequentlyAskedQuestions

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.ExpandableCard
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.KanguroMotionLayoutContainer
import com.insurtech.kanguro.designsystem.ui.composables.faq.model.QuestionModel
import com.insurtech.kanguro.designsystem.ui.composables.homeFrequentlyAskedQuestions.model.HomeDashboardFaqModel
import com.insurtech.kanguro.designsystem.ui.composables.homeFrequentlyAskedQuestions.model.HomeFrequentlyAskedQuestionsEvent
import com.insurtech.kanguro.designsystem.ui.theme.MobaBodyBold
import com.insurtech.kanguro.designsystem.ui.theme.MobaHeadline
import com.insurtech.kanguro.designsystem.ui.theme.MobaSubheadBold
import com.insurtech.kanguro.designsystem.ui.theme.MobaSubheadRegular
import com.insurtech.kanguro.designsystem.ui.theme.PrimaryDarkest
import com.insurtech.kanguro.designsystem.ui.theme.SecondaryMedium
import com.insurtech.kanguro.designsystem.ui.theme.White

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeFrequentlyAskedQuestionsScreenContent(
    model: HomeDashboardFaqModel,
    isLoading: Boolean,
    isError: Boolean,
    onEvent: (HomeFrequentlyAskedQuestionsEvent) -> Unit
) {
    val pullRefreshState = rememberPullRefreshState(
        refreshing = false,
        onRefresh = {
            onEvent(HomeFrequentlyAskedQuestionsEvent.OnPullToRefresh)
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
                onEvent(HomeFrequentlyAskedQuestionsEvent.OnBackPressed)
            },
            onTryAgainPressed = {
                onEvent(HomeFrequentlyAskedQuestionsEvent.OnTryAgainPressed)
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
    model: HomeDashboardFaqModel,
    isLoading: Boolean,
    isError: Boolean,
    onBackPressed: () -> Unit,
    onTryAgainPressed: () -> Unit
) {
    KanguroMotionLayoutContainer(
        image = R.drawable.img_home_faq_banner,
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

            if (model.petFaq.isNotEmpty()) {
                if (model.hasOnlyPetFaq.not()) {
                    Text(
                        text = stringResource(id = R.string.pet_insurance_faq),
                        style = MobaBodyBold.copy(color = PrimaryDarkest)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Faq(model.petFaq)

                Spacer(modifier = Modifier.height(32.dp))
            }

            if (model.rentersFaq.isNotEmpty()) {
                if (model.hasOnlyRentersFaq.not()) {
                    Text(
                        text = stringResource(id = R.string.renters_insurance_faq),
                        style = MobaBodyBold.copy(color = PrimaryDarkest)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Faq(model.rentersFaq)

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
private fun Faq(questions: List<QuestionModel>) {
    Column(
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        questions.forEach { item ->
            ExpandableCard(
                title = item.question,
                isExpanded = item.isExpanded,
                onClick = {
                    if (item.isExpanded) {
                        item.isExpanded = false
                    } else {
                        questions.forEach { it.isExpanded = false }
                        item.isExpanded = !item.isExpanded
                    }
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
private fun RentersFrequentlyAskedQuestionsScreenContentPreview() {
    HomeFrequentlyAskedQuestionsScreenContent(
        model = HomeDashboardFaqModel(
            rentersFaq = listOf(
                QuestionModel(
                    question = "What does a Renters Insurance plan for you and your belongings mean?",
                    answer = "This is what we call insurance for the day-to day routine! By having access to huge savings on preventive care mom and dad would have wanted and done anyways. Kanguro wants to offer more than just a coverage for when there is an unfortunate accident or illness. We want to promote a healthy and long-life offering coverage that will help your pocket and prevent unnecessary surprises."
                ),
                QuestionModel(
                    question = "Question 2",
                    answer = "Answer 2"
                ),
                QuestionModel(
                    question = "Question 3",
                    answer = "Answer 3"
                ),
                QuestionModel(
                    question = "Question 4",
                    answer = "Answer 4"
                )
            ),
            petFaq = listOf(
                QuestionModel(
                    question = "Do I need my pets medical history?",
                    answer = "It is very important for us to get to know your furry family member and understand their current condition! This helps us process your claim faster! Kanguro needs to review the medical history at the beginning and throughout the policy period as well."
                ),
                QuestionModel(
                    question = "Question 2",
                    answer = "Answer 2"
                ),
                QuestionModel(
                    question = "Question 3",
                    answer = "Answer 3"
                ),
                QuestionModel(
                    question = "Question 4",
                    answer = "Answer 4"
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
private fun RentersFrequentlyAskedQuestionsScreenContentLoadingPreview() {
    HomeFrequentlyAskedQuestionsScreenContent(
        model = HomeDashboardFaqModel(),
        isLoading = true,
        isError = false,
        onEvent = {}
    )
}

@Composable
@Preview
private fun RentersFrequentlyAskedQuestionsScreenContentErrorPreview() {
    HomeFrequentlyAskedQuestionsScreenContent(
        model = HomeDashboardFaqModel(),
        isLoading = false,
        isError = true,
        onEvent = {}
    )
}
