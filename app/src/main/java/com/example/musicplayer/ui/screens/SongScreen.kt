package com.example.musicplayer.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.musicplayer.network.Song

@Composable
fun SongScreen(
    song: Song,
    onSongSelected: (Song) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = { onSongSelected(song) }),
        verticalAlignment = Alignment.CenterVertically
    ) {

        AsyncImage(
            model = "https://cms.samespace.com/assets/" + song.cover,
            contentDescription = null,
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop,
            alignment =  Alignment.CenterStart
        )

        Spacer(modifier = Modifier.size(4.dp))

        Column {
            Text(
                text = song.name,
                fontSize = 16.sp
            )

            Text(
                text = song.artist,
                fontSize =  12.sp
            )
        }
    }
}