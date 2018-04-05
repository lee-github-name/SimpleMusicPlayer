package com.lxl.simplemusicplayer.service

import android.media.MediaPlayer
import com.lxl.simplemusicplayer.entity.MusicInfo

interface IPlayMusic {

    fun startMusic(url: String)

    fun pauseOrResumeMusic()

    fun preMusic(url: String)

    fun nextMusic(url: String)

}