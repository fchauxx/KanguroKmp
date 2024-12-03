package com.insurtech.kanguro.designsystem.ui.composables.homeDashboard

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.ui.theme.MobaTitle1
import com.insurtech.kanguro.designsystem.ui.theme.PrimaryDarkest
import kotlinx.coroutines.delay

@Composable
fun PetDynamicTitle(modifier: Modifier = Modifier, userName: String, petsNames: List<String>) {
    var currentPetName by remember { mutableStateOf(petsNames.first()) }
    val animatedProgress = remember { Animatable(0f) }
    val animationFinished = remember { mutableStateOf(false) }

    LaunchedEffect(currentPetName) {
        animatedProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 50 * currentPetName.length, easing = LinearEasing)
        )
        delay(1500L)
        animatedProgress.animateTo(
            targetValue = 0f,
            animationSpec = tween(durationMillis = 50 * currentPetName.length, easing = LinearEasing)
        )
        animationFinished.value = true
    }

    LaunchedEffect(Unit) {
        while (true) {
            for (name in petsNames) {
                currentPetName = name
                animationFinished.value = false
                while (!animationFinished.value) delay(100)
            }
        }
    }

    Text(
        modifier = modifier,
        style = MobaTitle1,
        text = buildAnnotatedString {
            append(stringResource(id = R.string.pet_greeting, userName))

            withStyle(style = SpanStyle(color = PrimaryDarkest)) {
                append(
                    text = " " + currentPetName.substring(
                        0,
                        (currentPetName.length * animatedProgress.value).toInt()
                    )
                )
            }
        }
    )
}

@Preview
@Composable
fun PetDynamicTitlePreview() {
    Surface {
        PetDynamicTitle(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            userName = "Lauren",
            petsNames = listOf("Bobby", "Luna", "Arnold")
        )
    }
}
