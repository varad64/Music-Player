package com.example.musicplayer.network

import retrofit2.http.GET

interface SongApiService {
    @GET("songs")
    suspend fun getSongs(): SongResponse
}