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
    lateinit var preButton: Button
    lateinit var pauseButton: Button
    lateinit var nextButton: Button
    lateinit var playMusicService: PlayMusicService
    private var binded = false
    private lateinit var data: ArrayList<MusicInfo>
    private var currentPlayMusicPosition = 0
    private val connection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {

        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val playMusicBinder = service as PlayMusicService.PlayMusicBinder
            playMusicService = playMusicBinder.getService()
            playMusicService.startMusic(data[currentPlayMusicPosition].url)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("PlayMusicActivity","onCreate")
        setContentView(R.layout.activity_playmusic)
        initData(savedInstanceState)
        startPlayMusicService()
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
        if (!binded) {
            binded = true
            val intentService = Intent(this, PlayMusicService::class.java)
            bindService(intentService, connection, Context.BIND_AUTO_CREATE)
        }
    }

    private fun startPlayMusicService(){
        val intentService = Intent(this,PlayMusicService::class.java)
        startService(intentService)
    }

    private fun initView(){
        preButton = findViewById(R.id.pre_music)
        preButton.setOnClickListener {
            if (--currentPlayMusicPosition>=0) {
                Log.i("PlayMusicActivity", "currentPlayMusicPosition== $currentPlayMusicPosition")
                playMusicService.preMusic(data[currentPlayMusicPosition].url)
            }
        }
        pauseButton = findViewById(R.id.pause_resume)
        pauseButton.setOnClickListener {
            playMusicService.pauseOrResumeMusic()
        }
        nextButton = findViewById(R.id.next_music)
        nextButton.setOnClickListener {
            if (++currentPlayMusicPosition<data?.size!!)
                playMusicService.preMusic(data[currentPlayMusicPosition].url)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (binded) {
            unbindService(connection)
            binded = false
        }
    }
}
