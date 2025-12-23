package com.revidd.did.composable

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.compose.foundation.shape.CircleShape
import androidx.core.net.toUri
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import kotlinx.coroutines.delay

@Composable
fun VideoPlayerScreen(
    videoUrl: String,
    onBack: () -> Unit
) {

    val context = LocalContext.current
    val playerError = remember { mutableStateOf<String?>(null) }

    LaunchedEffect(playerError.value) {
        playerError.value?.let { msg ->
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
            delay(3000)
            onBack()
        }
    }
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build()
    }
    LaunchedEffect(videoUrl) {
        exoPlayer.addListener(object : Player.Listener {

            override fun onPlayerError(error: PlaybackException) {
                playerError.value =  "Playback failed: Going Back"
            }
        })

        val mediaItem = MediaItem.fromUri(videoUrl.toUri())
        exoPlayer.setMediaItem(mediaItem)
        exoPlayer.prepare()
        exoPlayer.playWhenReady = true
    }
    var playerView: PlayerView? by remember { mutableStateOf(null) }
    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }
    }

    Box(Modifier.fillMaxSize()) {

        AndroidView(
            factory = { ctx ->
                PlayerView(ctx).also {
                    it.player = exoPlayer
                    playerView = it
                }
            },
            modifier = Modifier.fillMaxSize()
        )

        IconButton(
            onClick = { onBack() },
            modifier = Modifier
                .padding(16.dp)
                .size(48.dp)
                .background(
                    Color.Black.copy(alpha = 0.5f),
                    shape = CircleShape
                )
        ) {
            Icon(
                Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color.White
            )
        }
    }
}
