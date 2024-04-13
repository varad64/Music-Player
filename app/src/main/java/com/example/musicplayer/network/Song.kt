package com.example.musicplayer.network

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.net.URL

@Serializable
data class SongResponse(
    val data: List<Song>
)

@Serializable
data class Song (
    val id: Int,
    val name: String,
    val artist: String,
    @SerialName(value = "top_track")
    val topTrack: Boolean,
    val cover: String,
    val url: String,
    val accent: String
)
