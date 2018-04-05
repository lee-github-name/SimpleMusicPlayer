package com.lxl.simplemusicplayer

import android.app.LoaderManager
import android.content.CursorLoader
import android.content.Intent
import android.content.Loader
import android.database.Cursor
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import com.lxl.simplemusicplayer.activity.PlayMusicActivity
import com.lxl.simplemusicplayer.adapter.MusicInfoAdapter
import com.lxl.simplemusicplayer.entity.MusicInfo
import com.lxl.simplemusicplayer.service.PlayMusicService
import android.provider.MediaStore.Audio.Media.*

class MainActivity : AppCompatActivity() , LoaderManager.LoaderCallbacks<Cursor> {
    private  val  musicInfoList: ArrayList<MusicInfo> = ArrayList()
    private lateinit var musicInfoAdapter: MusicInfoAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loaderManager.initLoader(0,null,this)
        initView()
    }

    private fun initView() {
        val musicListView = findViewById<ListView>(R.id.music_view)
        musicInfoAdapter = MusicInfoAdapter(this, musicInfoList)
        musicListView.adapter = musicInfoAdapter
        musicListView.setOnItemClickListener { _, _, position, id ->
            startPlayMusicActivity(position)
        }
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        val projection = arrayOf(_ID,
                TITLE,
                ALBUM,
                ARTIST,
                DISPLAY_NAME,
                DURATION,
                SIZE,
                DATA)
        val selection = """ ($DATA LIKE '%.mp3') """
        return CursorLoader(this,EXTERNAL_CONTENT_URI,projection,selection, null,null)
    }

    override fun onLoadFinished(loader: Loader<Cursor>?, data: Cursor?) {
        musicInfoList.clear()
        musicInfoList.addAll(parseMusicInfoFromCursor(data))
        musicInfoAdapter.notifyDataSetChanged()
    }

    override fun onLoaderReset(loader: Loader<Cursor>?) {

    }

    private fun parseMusicInfoFromCursor(cursor: Cursor?): ArrayList<MusicInfo>{
        val musicInfoList = ArrayList<MusicInfo>()
        cursor?.apply {
            while (moveToNext()){
                val id = getLong(getColumnIndex(_ID))
                val title = getString(getColumnIndex(TITLE))
                val album = getString(getColumnIndex(ALBUM))
                val artist = getString(getColumnIndex(ARTIST))
                val displayname = getString(getColumnIndex(DISPLAY_NAME))
                val duration = getLong(getColumnIndex(DURATION))
                val size = getLong(getColumnIndex(SIZE))
                val url = getString(getColumnIndex(DATA))
                val musicInfo = MusicInfo(id,title,album,artist,displayname,duration,size,url)
                musicInfoList.add(musicInfo)
            }
        }
        return musicInfoList
    }

    private fun startPlayMusicService(){
        val intentService = Intent(this,PlayMusicService::class.java)
        startService(intentService)
    }

    private fun startPlayMusicActivity(position: Int){
        startPlayMusicService()
        val intentPlayMusic = Intent(this@MainActivity,PlayMusicActivity::class.java)
        intentPlayMusic.putExtra("url",musicInfoList )
        intentPlayMusic.putExtra("position",position)
        startActivity(intentPlayMusic)
    }

    override fun onDestroy() {
        super.onDestroy()
        val intentService = Intent(this, PlayMusicService::class.java)
        stopService(intentService)
    }
}
