package com.lxl.simplemusicplayer.engine

import android.app.Activity
import android.app.LoaderManager
import android.content.Context
import android.content.CursorLoader
import android.content.Loader
import android.database.Cursor
import android.os.Bundle
import android.provider.MediaStore
import android.provider.MediaStore.Audio.Media.*
import com.lxl.simplemusicplayer.MainActivity
import com.lxl.simplemusicplayer.entity.MusicInfo

class MusicContent(private val context: Activity) : LoaderManager.LoaderCallbacks<Cursor> {

    init {
         context.loaderManager.initLoader(0,null,this)
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
        return CursorLoader(context,EXTERNAL_CONTENT_URI,projection,null, null,null)
    }

    override fun onLoadFinished(loader: Loader<Cursor>?, data: Cursor?) {
        val mainActivity = context as MainActivity
//        mainActivity.musicInfoList = parseMusicInfoFromCursor(data)
    }

    override fun onLoaderReset(loader: Loader<Cursor>?) {

    }

    fun getMusicInfo(): List<MusicInfo>{
        val projection = arrayOf(MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.SIZE,
                MediaStore.Audio.Media.DATA)
        val cursor = context.contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,projection,null,null,null)
        return parseMusicInfoFromCursor(cursor)
    }

    /**
     * 可以优化 （用kotlin的方式）
     */
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
}