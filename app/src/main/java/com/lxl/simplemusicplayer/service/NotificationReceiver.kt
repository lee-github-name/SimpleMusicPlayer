package com.lxl.simplemusicplayer.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.lxl.simplemusicplayer.constant.Constant

class NotificationReceiver : BroadcastReceiver() {


    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
           Constant.pauseOrResumeAction -> context.run {
               val serviceIntent = Intent(this,PlayMusicService::class.java)
               serviceIntent.action = intent.action
               startService(serviceIntent)
           }
        }
    }

}
