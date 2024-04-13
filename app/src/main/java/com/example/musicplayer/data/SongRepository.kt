package com.example.musicplayer.data

import com.example.musicplayer.network.SongApiService
import com.example.musicplayer.network.SongResponse

interface SongRepository {
    suspend fun getSongs(): SongResponse
}

class NetworkSongRepository(private val songApiService: SongApiService): SongRepository {
    override suspend fun getSongs(): SongResponse {
        return songApiService.getSongs()
    }
}