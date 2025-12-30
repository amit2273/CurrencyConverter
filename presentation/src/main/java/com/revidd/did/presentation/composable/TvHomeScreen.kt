package com.revidd.did.presentation.composable

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TvHomeScreen() {
    Row(
        modifier = Modifier.fillMaxSize()
    ) {
        SideNavRail(
            modifier = Modifier.width(88.dp)
        )

        MainContent(
            modifier = Modifier.weight(1f)
        )
    }
}
