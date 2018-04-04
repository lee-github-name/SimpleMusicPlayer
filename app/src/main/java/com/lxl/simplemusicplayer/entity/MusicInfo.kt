package com.lxl.simplemusicplayer.entity

data class MusicInfo(val id: Long,
                     val title: String,
                     val album: String,
                     val artist: String,
                     val displayName: String,
                     val duration: Long,
                     val size: Long,
                     val url: String)