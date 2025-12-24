package com.revidd.did.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.revidd.did.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onTimeout: () -> Unit
) {
    LaunchedEffect(Unit) {
        delay(2000) // 2 seconds
        onTimeout()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.revidd_splash_logo),
            contentDescription = "App Logo",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
    }
}
