package com.revidd.did.presentation.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SideNavRail(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxHeight()
            .background(Color(0xFF0F172A))
            .padding(vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TvNavItem("Home")
        TvNavItem("Movies")
        TvNavItem("Shows")
        TvNavItem("Search")
        TvNavItem("Settings")
    }
}

@Composable
fun TvNavItem(title: String) {
    Text(
        text = title,
        color = Color.White,
        modifier = Modifier
            .focusable()
            .padding(12.dp)
    )
}
