package com.insurtech.kanguro.designsystem.ui.composables.rentersEditAdditionalParties.sections

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.W700
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model.PartyItemModelRelation
import com.insurtech.kanguro.designsystem.ui.theme.LatoBoldNeutral
import com.insurtech.kanguro.designsystem.ui.theme.LatoRegularSecondaryDarkSize12
import com.insurtech.kanguro.designsystem.ui.theme.SecondaryLightest
import com.insurtech.kanguro.designsystem.ui.theme.SecondaryMedium
import com.insurtech.kanguro.designsystem.ui.theme.TertiaryDark

@Composable
fun AdditionalPartyCard(
    modifier: Modifier = Modifier,
    name: String,
    relation: PartyItemModelRelation,
    onEditPressed: () -> Unit,
    onDeletePressed: () -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, SecondaryLightest)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 19.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = name,
                    style = LatoRegularSecondaryDarkSize12.copy(
                        fontWeight = W700,
                        fontSize = 13.sp
                    )
                )

                Text(
                    text = relation.name,
                    style = LatoBoldNeutral.copy(
                        color = TertiaryDark,
                        fontWeight = W700,
                        fontSize = 12.sp
                    )
                )
            }
            Row {
                IconButton(
                    modifier = Modifier.size(24.dp),
                    onClick = onEditPressed
                ) {
                    Image(
                        modifier = Modifier.size(20.dp),
                        painter = painterResource(id = R.drawable.ic_edit),
                        colorFilter = ColorFilter.tint(SecondaryMedium),
                        contentDescription = stringResource(R.string.edit_additional_party)

                    )
                }

                Spacer(modifier = Modifier.width(2.dp))

                IconButton(
                    modifier = Modifier.size(24.dp),
                    onClick = onDeletePressed
                ) {
                    Image(
                        modifier = Modifier.size(20.dp),
                        painter = painterResource(id = R.drawable.ic_trash),
                        contentDescription = stringResource(R.string.delete_additional_party)

                    )
                }
            }
        }
    }
}

@Composable
@Preview
fun AdditionalPartCardPreview() {
    AdditionalPartyCard(
        name = "Olivia Roberts",
        relation = PartyItemModelRelation.Roommate,
        onDeletePressed = {},
        onEditPressed = {}
    )
}
