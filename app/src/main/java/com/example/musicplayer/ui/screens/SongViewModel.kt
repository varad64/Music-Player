package com.example.musicplayer.ui.screens

import android.content.Context
import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.musicplayer.MusicApplication
import com.example.musicplayer.MusicPlayer
import com.example.musicplayer.data.NetworkSongRepository
import com.example.musicplayer.data.SongRepository
import com.example.musicplayer.network.Song
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed interface SongUiState {
    data class Success(val songs: String): SongUiState
    object Loading: SongUiState
    object Error: SongUiState
}

class SongViewModel(
    private val songRepository: SongRepository,
    private val context: Context
): ViewModel() {
    var songUiState: SongUiState by mutableStateOf(SongUiState.Loading)
        private set

    private var _songState: MutableStateFlow<Song> = MutableStateFlow(Song(1, "", "", false, "", "", accent = ""))
    val songState: StateFlow<Song> = _songState.asStateFlow()

    val musicPlayer = MusicPlayer(context = context)
    var playList: MutableList<Song> = mutableListOf()

    init {
        getSongs()
    }

    fun getSongs() {
        try {
            viewModelScope.launch {
                val listResult = songRepository.getSongs()
                songUiState =
                    SongUiState.Success("Success! Fetched ${listResult.data.size} number of songs")
                listResult.data.forEach { song ->
                    musicPlayer.addSongToPlayList(song.url)
                    playList.add(song)
                }
            }
        } catch (e: Exception) {
            Log.d("@@@ERROR", e.message?:"")
        }
    }

    fun setSong(song: Song) {
        _songState.update { currentState ->
            currentState.copy(
                id = song.id,
                name = song.name,
                artist = song.artist,
                topTrack = song.topTrack,
                cover = song.cover,
                url = song.url,
                accent = song.accent
            )
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as MusicApplication)
                val songRepository = application.container.songRepository
                SongViewModel(songRepository = songRepository, context = application.applicationContext)
            }
        }
    }
}