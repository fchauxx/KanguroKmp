package com.insurtech.kanguro.designsystem.ui.composables.directPayToVet.directPayToVetInitFlow

import android.widget.TextView
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.compose.ConstraintLayout
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.ui.theme.MobaBodyRegularSDark

@Composable
fun DirectPayToVetInstruction(
    modifier: Modifier = Modifier,
    @StringRes textId: Int
) {
    ConstraintLayout(
        modifier = modifier
    ) {
        val (dot, instruction) = createRefs()

        Text(
            text = stringResource(id = R.string.middle_dot),
            style = MobaBodyRegularSDark,
            modifier = Modifier
                .constrainAs(dot) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
        )

        StyledText(
            text = LocalContext.current.resources.getText(textId),
            modifier = Modifier
                .padding(start = 10.dp)
                .constrainAs(instruction) {
                    start.linkTo(dot.end)
                    top.linkTo(parent.top)
                }
        )
    }
}

@Composable
private fun StyledText(
    text: CharSequence,
    modifier: Modifier = Modifier
) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            TextView(context).apply {
                setTextAppearance(R.style.MobaBodyRegular)
            }
        },
        update = {
            it.text = text
        }
    )
}

@Preview
@Composable
fun DirectPayToVetInstructionPreview() {
    Surface {
        DirectPayToVetInstruction(
            textId = R.string.accident_and_illness,
            modifier = Modifier.padding(16.dp)
        )
    }
}
