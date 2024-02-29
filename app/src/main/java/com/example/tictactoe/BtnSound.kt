package com.example.tictactoe

import android.app.Activity
import android.media.MediaPlayer

object BtnSound {

    fun buttonSound(activity:Activity){
        val mp: MediaPlayer = MediaPlayer.create(activity,R.raw.buttonsound)
        mp.setVolume(0.3f,0.3f)   // SETTING THE VOLUME TO 50%
        mp.start()
        mp.setOnCompletionListener{       // ON COMPLETION RELEASE TO AVOID MEMORY LEAK
            mp.release()
        }
    }
}