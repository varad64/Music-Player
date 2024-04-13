package com.example.musicplayer.data

import com.example.musicplayer.network.SongApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val songRepository: SongRepository
}

class DefaultAppContainer: AppContainer {
    private val baseUrl = "https://cms.samespace.com/items/"

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(Json {
            ignoreUnknownKeys = true
        }.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    val retrofitService: SongApiService by lazy {
        retrofit.create(SongApiService::class.java)
    }

    override val songRepository: SongRepository by lazy {
        NetworkSongRepository(retrofitService)
    }
}