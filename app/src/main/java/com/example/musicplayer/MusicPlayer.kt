package com.example.musicplayer

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.example.musicplayer.ui.screens.SongViewModel

class MusicPlayer(val context: Context) {
    val player = ExoPlayer.Builder(context).build()

    fun addSongToPlayList(mediaItem: String) {
        player.addMediaItem(MediaItem.fromUri(mediaItem))
    }
}