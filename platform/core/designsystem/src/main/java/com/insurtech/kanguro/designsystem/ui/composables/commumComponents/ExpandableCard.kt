package com.insurtech.kanguro.designsystem.ui.composables.commumComponents

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.ui.theme.MobaBodyBold
import com.insurtech.kanguro.designsystem.ui.theme.NeutralBackground
import com.insurtech.kanguro.designsystem.ui.theme.PrimaryMedium

@Composable
fun ExpandableCardStateful(
    modifier: Modifier = Modifier,
    title: String,
    @DrawableRes icon: Int? = null,
    backgroundColor: Color = NeutralBackground,
    iconTint: Color? = null,
    content: @Composable () -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }

    ExpandableCard(
        modifier = modifier,
        title = title,
        icon = icon,
        backgroundColor = backgroundColor,
        iconTint = iconTint,
        isExpanded = isExpanded,
        onClick = { isExpanded = !isExpanded }
    ) {
        content()
    }
}

@Composable
fun ExpandableCard(
    modifier: Modifier = Modifier,
    title: String,
    @DrawableRes icon: Int? = null,
    backgroundColor: Color = NeutralBackground,
    isExpanded: Boolean = false,
    onClick: () -> Unit = {},
    iconTint: Color? = null,
    content: @Composable () -> Unit
) {
    CardBox(modifier, backgroundColor, isExpanded) {
        Header(title, icon, isExpanded, iconTint, onClick)
        AnimatedVisibility(
            visible = isExpanded
        ) {
            content()
        }
    }
}

@Composable
private fun CardBox(
    modifier: Modifier = Modifier,
    backgroundColor: Color,
    isExpanded: Boolean,
    content: @Composable ColumnScope.() -> Unit
) {
    val transition = updateTransition(targetState = isExpanded, label = "borderColorTransition")

    val borderColor by transition.animateColor(
        transitionSpec = { tween(durationMillis = 500) },
        label = "borderColorAnimation"
    ) { expanded -> if (expanded) PrimaryMedium else Color.Transparent }

    Surface(
        modifier = modifier,
        color = backgroundColor,
        border = BorderStroke(2.dp, borderColor),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            content()
        }
    }
}

@Composable
private fun Header(
    title: String,
    @DrawableRes icon: Int? = null,
    isExpanded: Boolean,
    iconTint: Color? = null,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(0.75f),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (icon != null) {
                Image(
                    painter = painterResource(id = icon),
                    contentDescription = title,
                    colorFilter = if (iconTint != null) {
                        ColorFilter.tint(iconTint)
                    } else {
                        null
                    }
                )
            }
            Text(text = title, style = MobaBodyBold)
        }

        Spacer(modifier = Modifier.width(16.dp))

        Image(
            painter = if (isExpanded) {
                painterResource(R.drawable.ic_chevron_up)
            } else {
                painterResource(
                    R.drawable.ic_chevron_down
                )
            },
            contentDescription = ""
        )
    }
}

@Composable
@Preview
private fun ExpandableCardCollapsedPreview() {
    Surface {
        ExpandableCard(
            modifier = Modifier.padding(16.dp),
            title = "Expandable Card Title"
        ) {
            Text(
                modifier = Modifier.padding(top = 16.dp),
                text = "Any Content Here"
            )
        }
    }
}

@Composable
@Preview
private fun ExpandableCardCollapsedWithIconPreview() {
    Surface {
        ExpandableCard(
            modifier = Modifier.padding(16.dp),
            title = "Expandable Card With Icon Title",
            icon = R.drawable.ic_gallery
        ) {
            Text(
                modifier = Modifier.padding(top = 16.dp),
                text = "Any Content Here"
            )
        }
    }
}

@Composable
@Preview
private fun ExpandableCardExpandedPreview() {
    Surface {
        ExpandableCard(
            modifier = Modifier.padding(16.dp),
            title = "Expandable Card Title",
            isExpanded = true
        ) {
            Text(
                modifier = Modifier.padding(top = 16.dp),
                text = "Any Content Here"
            )
        }
    }
}

@Composable
@Preview
private fun ExpandableCardStatefulPreview() {
    Surface {
        ExpandableCardStateful(
            modifier = Modifier.padding(16.dp),
            title = "Expandable Card Title"
        ) {
            Text(
                modifier = Modifier.padding(top = 16.dp),
                text = "Any Content Here"
            )
        }
    }
}
