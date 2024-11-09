package me.algosketch.algoedit.presentation.screen.edition

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import me.algosketch.algoedit.design.component.TmpAppBar
import me.algosketch.algoedit.design.theme.MyApplicationTheme

@Composable
fun EditionScreen(
    viewModel: EditionViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            val mediaItem = MediaItem.fromUri("https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4")
            setMediaItem(mediaItem)
            prepare()
            playWhenReady = true
        }
    }

    Column {
        TmpAppBar("Project(1)")
        Box(modifier = Modifier.size(300.dp)) {
            AndroidView(
                modifier = Modifier.fillMaxSize(),
                factory = {
                    PlayerView(it).apply {
                        player = exoPlayer
                    }
                },
            )
        }
    }
}

@Preview
@Composable
fun EditionScreenPreview() {
    MyApplicationTheme {
        EditionScreen()
    }
}