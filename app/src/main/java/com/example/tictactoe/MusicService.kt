package com.example.tictactoe

import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.app.Service
import android.util.Log



// THIS CLASS IS FOR BACKGROUND MUSIC

class MusicService : Service() {

    private var mediaPlayer: MediaPlayer? = null

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        mediaPlayer = MediaPlayer.create(this, R.raw.backgroundmusic)
        mediaPlayer?.isLooping = true
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        mediaPlayer?.start()
        return START_STICKY
    }

    override fun onDestroy() {
        Log.d("MusicService", "onDestroy called",)

        mediaPlayer?.stop()
        mediaPlayer?.release()
        super.onDestroy()
    }
}