package com.insurtech.kanguro.designsystem.ui.composables.commumComponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.R

@Composable
fun AddCoverageButton(onAddPetPressed: () -> Unit) {
    IconButton(
        onClick = onAddPetPressed,
        modifier = Modifier.padding(8.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_add_policy),
            contentDescription = null
        )
    }
}
