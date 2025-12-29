package com.revidd.did.presentation.composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.tv.material3.Button
import androidx.tv.material3.Text

@Composable
fun TvPrimaryButton(text: String) {
    Button(
        onClick = {},
        modifier = Modifier
            .height(52.dp)
            .fillMaxWidth()
    ) {
        Text(text)
    }
}
