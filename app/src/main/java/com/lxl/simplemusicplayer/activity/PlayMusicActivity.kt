package com.lxl.simplemusicplayer.activity

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import com.lxl.simplemusicplayer.R
import com.lxl.simplemusicplayer.entity.MusicInfo
import com.lxl.simplemusicplayer.service.PlayMusicService

class PlayMusicActivity : AppCompatActivity() {
    lateinit var playMusicService: PlayMusicService
    private var bindService = false
    private lateinit var data: ArrayList<MusicInfo>
    private var currentPlayMusicPosition = 0
    private val connection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {

        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val playMusicBinder = service as PlayMusicService.PlayMusicBinder
            playMusicService = playMusicBinder.service
            playMusicService.startMusic(data[currentPlayMusicPosition].url)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("PlayMusicActivity","onCreate")
        setContentView(R.layout.activity_playmusic)
        initData(savedInstanceState)
        initView()
    }
    override fun onStart() {
        super.onStart()
        Log.i("PlayMusicActivity","onStart")
        bindPlayMusicService()
    }
    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        Log.i("PlayMusicActivity","onSaveInstanceState")
        outState?.putSerializable("url",data)
        outState?.putInt("position",currentPlayMusicPosition)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.i("PlayMusicActivity","onRestoreInstanceState")
    }


    private fun initData(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            data = intent.getSerializableExtra("url") as ArrayList<MusicInfo>
            currentPlayMusicPosition = intent.getIntExtra("position", 0)
        } else {
            data = savedInstanceState.getSerializable("url") as ArrayList<MusicInfo>
            currentPlayMusicPosition = savedInstanceState.getInt("position")
        }
    }



    private fun bindPlayMusicService() {
        if (!bindService) {
            bindService = true
            val intentService = Intent(this, PlayMusicService::class.java)
            bindService(intentService, connection, Context.BIND_AUTO_CREATE)
        }
    }



    private fun initView(){
        val preButton = findViewById<Button>(R.id.pre_music)
        preButton.setOnClickListener {
            if (--currentPlayMusicPosition>=0) {
                Log.i("PlayMusicActivity", "currentPlayMusicPosition== $currentPlayMusicPosition")
                playMusicService.preMusic(data[currentPlayMusicPosition].url)
            }
        }
        val pauseButton = findViewById<Button>(R.id.pause_resume)
        pauseButton.setOnClickListener {
            playMusicService.pauseOrResumeMusic()
        }
        val nextButton = findViewById<Button>(R.id.next_music)
        nextButton.setOnClickListener {
            if (++currentPlayMusicPosition<data?.size!!)
                playMusicService.preMusic(data[currentPlayMusicPosition].url)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (bindService) {
            unbindService(connection)
            bindService = false
        }
    }
}
