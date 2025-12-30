package com.revidd.did.presentation.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ContentCard() {
    Box(
        modifier = Modifier
            .size(width = 220.dp, height = 320.dp)
            .background(Color.Gray, RoundedCornerShape(12.dp))
            .focusable()
    )
}
