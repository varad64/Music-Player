package com.example.musicplayer.ui.screens

import android.widget.Button
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import coil.compose.AsyncImage
import com.example.musicplayer.R
import com.example.musicplayer.network.Song

@Composable
fun ExpandablePlayerScreen(
    song: Song,
    isPlaying: Boolean,
    onPlayPauseToggle: () -> Unit,
    onPrevious: () -> Unit,
    onNext: () -> Unit,
    onSeekBarChange: (Long) -> Unit,
    currentPos: Long,
    songDuration: Long,
    modifier: Modifier = Modifier
        .fillMaxSize()
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = Color(song.accent.toColorInt())),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Cover image
        AsyncImage(
            model = "https://cms.samespace.com/assets/" + song.cover,
            contentDescription = song.name,
            modifier = Modifier.size(400.dp),
            contentScale = ContentScale.Crop
        )

//        Text(text = isPlaying.toString() + " " +  currentPos + " cp in float = " + currentPos.toFloat() + " and duration = " + songDuration + " f = " + songDuration.toFloat())

        Slider(
            value = currentPos.toFloat(),
            onValueChange = { },
            valueRange = 0f..10f,
            onValueChangeFinished = { /* Update playback position */ }
        )

        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            IconButton(onClick = onPrevious) {
                Icon(painterResource(R.drawable.skip_previous), contentDescription = "Previous")
            }
            IconButton(onClick = onPlayPauseToggle) {
                Icon(
                    if (isPlaying) painterResource(R.drawable.pause) else painterResource(R.drawable.play),
                    contentDescription = "Play/Pause"
                )
            }
            IconButton(onClick = onNext) {
                Icon(painterResource(R.drawable.skip_next), contentDescription = "Next")
            }
        }
    }
}