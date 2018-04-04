package com.lxl.simplemusicplayer

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import com.lxl.simplemusicplayer.activity.PlayMusicActivity
import com.lxl.simplemusicplayer.adapter.MusicInfoAdapter
import com.lxl.simplemusicplayer.engine.MusicContent
import com.lxl.simplemusicplayer.entity.MusicInfo
import java.text.FieldPosition

class MainActivity : AppCompatActivity() {
    private lateinit var  mMusicContent: MusicContent
    private lateinit var  musicListView: ListView
    private lateinit var  musicInfoList: List<MusicInfo>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mMusicContent = MusicContent(this)
        musicInfoList = mMusicContent.getMusicInfo()
        Log.i("MainActivity", musicInfoList.toString())
        musicListView = findViewById(R.id.music_view)
        musicListView.adapter = MusicInfoAdapter(this,musicInfoList)
        musicListView.setOnItemClickListener { _, _, position, id ->
            Log.i("MainActivity","listView==position=$position,id=$id")
            startPlayMusicActivity(position)
        }
    }
    private fun startPlayMusicActivity(position: Int){
        val intentPlayMusic = Intent(this@MainActivity,PlayMusicActivity::class.java)
        intentPlayMusic.putExtra("url",musicInfoList[position].url)
        startActivity(intentPlayMusic)
    }
}
