package com.insurtech.kanguro.designsystem.ui.composables.commumComponents

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.domain.EMAIL_TAG
import com.insurtech.kanguro.designsystem.ui.theme.TertiaryExtraDark

@Composable
fun getSupportEmailText(
    @StringRes messageId: Int,
    @StringRes emailId: Int
): AnnotatedString {
    val email = stringResource(id = emailId)

    return buildAnnotatedString {
        append(stringResource(id = messageId))
        append(" ")
        pushStringAnnotation(
            tag = EMAIL_TAG,
            annotation = email
        )
        withStyle(
            style = SpanStyle(
                color = TertiaryExtraDark,
                textDecoration = TextDecoration.Underline
            )
        ) {
            append(email)
        }
        pop()
    }
}
