package com.lxl.simplemusicplayer.service

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.support.v4.app.NotificationCompat
import android.widget.RemoteViews
import com.lxl.simplemusicplayer.R
import com.lxl.simplemusicplayer.activity.PlayMusicActivity
import com.lxl.simplemusicplayer.constant.Constant


class PlayMusicService : Service() , IPlayMusic{
    private val mediaPlayer: MediaPlayer by lazy {
        MediaPlayer()
    }
    private val foregroundId = 101
    override fun onCreate() {
        super.onCreate()
        initForegroundService()
    }

    private fun initForegroundService(){
        val remoteView = RemoteViews(packageName, R.layout.notification_layout)
        remoteView.setTextViewText(R.id.notification_title,"foreground service")
        val pendingIntent = PendingIntent.getBroadcast(this,0,Intent(Constant.pauseOrResumeAction),PendingIntent.FLAG_UPDATE_CURRENT)
        remoteView.setOnClickPendingIntent(R.id.pause_resume,pendingIntent)
        val notification =  with(NotificationCompat.Builder(this,"channelId")){
            setCustomBigContentView(remoteView)
            setSmallIcon(R.mipmap.ic_launcher_round)
            build()
        }
//        NotificationCompat.Builder(this,"channelId").setCustomBigContentView(remoteView).build()
        startForeground(foregroundId,notification)
    }


    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        when(intent.action){
            Constant.pauseOrResumeAction -> pauseOrResumeMusic()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent): IBinder? {
        return PlayMusicBinder()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
        stopForeground(true)
    }

    override fun startMusic(url: String) {
        mediaPlayer.reset()
        mediaPlayer.setDataSource(url)
        mediaPlayer.prepare()
        mediaPlayer.start()
    }

    override fun pauseOrResumeMusic() {
        if (mediaPlayer.isPlaying)
            mediaPlayer.pause()
        else
            mediaPlayer.start()
    }

    override fun preMusic(url: String) {
        mediaPlayer.reset()
        mediaPlayer.setDataSource(url)
        mediaPlayer.prepare()
        mediaPlayer.start()
    }

    override fun nextMusic(url: String) {
        mediaPlayer.reset()
        mediaPlayer.setDataSource(url)
        mediaPlayer.prepare()
        mediaPlayer.start()
    }

    inner class PlayMusicBinder : Binder()  {
        val service: PlayMusicService
            get() = this@PlayMusicService
    }

}
