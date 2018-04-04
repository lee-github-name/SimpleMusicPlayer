package com.lxl.simplemusicplayer.engine

import android.content.Context
import android.database.Cursor
import android.provider.MediaStore
import com.lxl.simplemusicplayer.entity.MusicInfo

class MusicContent(private val context: Context) {

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
    private fun parseMusicInfoFromCursor(cursor: Cursor): List<MusicInfo>{
        val musicInfoList = ArrayList<MusicInfo>()
        while (cursor.moveToNext()){
            val id = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID))
            val title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE))
            val album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM))
            val artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))
            val displayname = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME))
            val duration = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION))
            val size = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE))
            val url = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
            val musicInfo = MusicInfo(id,title,album,artist,displayname,duration,size,url)
            musicInfoList.add(musicInfo)
        }
        cursor.close()
        return musicInfoList
    }
}