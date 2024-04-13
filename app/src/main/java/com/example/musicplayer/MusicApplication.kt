package com.example.musicplayer

import android.app.Application
import com.example.musicplayer.data.AppContainer
import com.example.musicplayer.data.DefaultAppContainer

class MusicApplication: Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}
