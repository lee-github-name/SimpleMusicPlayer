package com.lxl.simplemusicplayer.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import com.lxl.simplemusicplayer.R
import com.lxl.simplemusicplayer.service.PlayMusicService

class PlayMusicActivity : AppCompatActivity() {
    val action = "com.lxl.simplemusicplayer.PlayMusicService"
    lateinit var preButton: Button
    lateinit var pauseButton: Button
    lateinit var nextButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_playmusic)
        startAndBindService()
        initView()
    }

    private fun startAndBindService(){
        val intentService = Intent(this,PlayMusicService::class.java)
        intentService.putExtra("url",intent.getStringExtra("url"))
        startService(intentService)
    }
    private fun initView(){
        preButton = findViewById(R.id.pre_music)
        preButton.setOnClickListener {

        }
        pauseButton = findViewById(R.id.pause_resume)
        pauseButton.setOnClickListener {

        }
        nextButton = findViewById(R.id.next_music)
        nextButton.setOnClickListener {

        }
    }


}
