package com.revidd.did.presentation.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MainContent(
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {

        item {
            HeroBanner()
        }

        item {
            ContentRow(
                title = "Continue Watching"
            )
        }

        item {
            ContentRow(
                title = "Premium Movies"
            )
        }

        item {
            ContentRow(
                title = "Regional Languages"
            )
        }

        item {
            ContentRow(
                title = "Trending Now"
            )
        }
    }
}
