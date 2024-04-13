package com.example.musicplayer.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.common.C
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.musicplayer.network.Song

enum class MusicScreen {
    Playlist,
    Song
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MusicApp(
    viewModel: SongViewModel = viewModel(factory = SongViewModel.Factory),
    navController: NavHostController = rememberNavController()
) {
    val tabs = listOf("For You", "Top Tracks")
    var selectedTabIndex by remember { mutableStateOf(0) }
    var showTopTracks by remember { mutableStateOf(false) }
    val player = viewModel.musicPlayer.player
    val playlist = viewModel.playList
    
    Scaffold(
        bottomBar = {
            BottomAppBar {
                tabs.forEachIndexed { index, title ->
                    TextButton(
                        onClick = {
                            selectedTabIndex = index
                            showTopTracks = selectedTabIndex == 1
                        },
                        modifier = Modifier.weight(1f),
                        content = { Text(title) }
                    )
                }
            }
        }
    ) { innerPadding ->
        val songState = viewModel.songState.collectAsState()

        NavHost(
            navController = navController,
            startDestination = MusicScreen.Playlist.name,
            modifier = Modifier.padding(8.dp)
        ) {
            composable(route = MusicScreen.Playlist.name) {
                if(viewModel.songUiState is SongUiState.Success) {
                    LazyColumn(modifier = Modifier.padding(innerPadding)) {
                        if(showTopTracks) {
                            val topTracks = playlist.filter {
                                val song: Song = it
                                song.topTrack
                            }
                            items(topTracks) {song ->
                                SongScreen(
                                    song = song,
                                    onSongSelected = {
                                        viewModel.setSong(it)
                                        navController.navigate(MusicScreen.Song.name)
                                    }
                                )
                            }
                        } else {
                            items(playlist) {song ->
                                SongScreen(
                                    song = song,
                                    onSongSelected = {
                                        viewModel.setSong(it)
                                        navController.navigate(MusicScreen.Song.name)
                                    }
                                )
                            }
                        }
                    }
                }
            }

            composable(route = MusicScreen.Song.name) {
                var currentSongIndex: Int
                ExpandablePlayerScreen(
                    song = viewModel.songState.collectAsState().value,
                    isPlaying = player.isPlaying,
                    onPlayPauseToggle = {
                        if(player.isPlaying) {
                            player.pause()
                        } else {
                            player.seekTo(playlist.indexOf(songState.value), C.TIME_UNSET)
                            player.prepare()
                            player.play()
                        }
                    },
                    onPrevious = {
                        currentSongIndex = playlist.indexOf(songState.value)
                        val prevIndex = if((currentSongIndex-1) >= 0) (currentSongIndex-1) else (playlist.size - 1)
                        viewModel.setSong(playlist[prevIndex])
                        player.seekToPreviousMediaItem()
                     },
                    onNext = {
                        currentSongIndex = playlist.indexOf(songState.value)
                        val nextIndex = if((currentSongIndex + 1) < playlist.size) (currentSongIndex+1) else 0
                        viewModel.setSong(playlist[nextIndex])
                        player.seekToNextMediaItem()
                     },
                    onSeekBarChange = {seekValue -> player.seekTo(seekValue)},
                    currentPos = player.currentPosition,
                    songDuration = player.duration
                )
            }
        }

    }
}