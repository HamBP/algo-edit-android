package me.algosketch.algoedit.presentation.screen.edition

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.arthenica.ffmpegkit.FFmpegKit
import me.algosketch.algoedit.design.component.TmpAppBar
import me.algosketch.algoedit.design.theme.MyApplicationTheme
import java.io.File
import java.io.FileInputStream
import java.io.OutputStream

@Composable
fun EditionScreen(
    viewModel: EditionViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    var uri: Uri? by remember { mutableStateOf(null) }
    val exoPlayer = remember(uri) {
        ExoPlayer.Builder(context).build().apply {
            if (uri != null) {
                val mediaItem = MediaItem.fromUri(uri!!)
                setMediaItem(mediaItem)
                prepare()
                playWhenReady = true
            }
        }
    }

    Column {
        TmpAppBar("Project(1)")
        Box(modifier = Modifier.size(300.dp)) {
            if (uri == null) {
                AddVideoButton({ uri = it })
            } else {
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
}

@Composable
fun AddVideoButton(
    onAddVideo: (uri: Uri) -> Unit,
) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            println("비디오 선택 ${uri}")
            val filePath = getFilePathFromUri(context, uri)
            if (filePath != null) {
                onAddVideo(uri)
            } else {
                println("111")
            }
        }
    }

    Button(
        onClick = { launcher.launch("video/*") }
    ) {
        Text(
            text = "동영상 추가"
        )
    }
}

@Composable
fun SimpleButton(name: String, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            println("비디오 선택 ${uri}")
            val filePath = getFilePathFromUri(context, uri)
            if (filePath != null) {
                runFfmpegCommand(context, filePath)
            } else {
                println("111")
            }
        }
    }

    Button(
        onClick = { launcher.launch("video/*") }
    ) {
        Text(
            text = name,
            modifier = modifier
        )
    }
}

fun getFilePathFromUri(context: Context, uri: Uri): String? {
    var filePath: String? = null
    val cursor = context.contentResolver.query(uri, null, null, null, null)
    cursor?.use {
        if (it.moveToFirst()) {
            val columnIndex = it.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)
            filePath = it.getString(columnIndex)
        }
    }
    return filePath
}

fun runFfmpegCommand(context: Context, filePath: String) {
    val volumes = ContextCompat.getExternalFilesDirs(context, null)
    val primaryStorage = volumes.first()
    val outputFilePath = "${primaryStorage.path}/output1.mp4"
    val command = "-i $filePath -ss 00:00:05 -to 00:00:10 -c copy $outputFilePath"

    FFmpegKit.executeAsync(command) { session ->
        val returnCode = session.returnCode
        if (returnCode.isValueSuccess) {
            println("성공")
            saveVideoToGallery(context, outputFilePath)
        } else {
            println("에러")
        }
    }
}

fun saveVideoToGallery(
    context: Context,
    sourceFilePath: String,
    fileName: String = "output_video.mp4"
) {
    val values = ContentValues().apply {
        put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
        put(MediaStore.MediaColumns.MIME_TYPE, "video/mp4")
        put(MediaStore.MediaColumns.RELATIVE_PATH, "Movies/MyAppVideos") // 갤러리 내에 저장할 경로
    }

    val resolver = context.contentResolver
    val uri = resolver.insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, values)

    if (uri != null) {
        // sourceFilePath의 파일을 갤러리에 복사
        val inputStream = FileInputStream(File(sourceFilePath))
        val outputStream: OutputStream? = resolver.openOutputStream(uri)

        inputStream.use { input ->
            outputStream?.use { output ->
                input.copyTo(output)
            }
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