package com.monotoshghosh.tictactoe

import android.app.Activity
import android.media.MediaPlayer
import com.monotoshghosh.tictactoe.R

object BtnSound {
    private var mediaPlayer: MediaPlayer? = null  // THIS WAS IMPLEMENTED FOR THE ----> fun stopMusic()

    fun buttonSound(activity:Activity){
        val mp: MediaPlayer = MediaPlayer.create(activity, R.raw.buttonsound)  //------------------- NEED TO DELETE---------------------
        mp.setVolume(0.3f,0.3f)   // SETTING THE VOLUME TO 30%
        mp.start()
        mp.setOnCompletionListener{       // ON COMPLETION RELEASE TO AVOID MEMORY LEAK
            mp.release()
        }
    }

    fun buttonErrorSound(activity: Activity){       // I3
        val mp: MediaPlayer = MediaPlayer.create(activity, R.raw.errorsound)
        mp.setVolume(0.8f,0.8f)
        mp.start()
        mp.setOnCompletionListener{
            mp.release()
        }
    }
    fun buttonResetSound(activity: Activity){  //       --------------------------------- NEED TO DELETE -----------------------------
        val mp: MediaPlayer = MediaPlayer.create(activity, R.raw.resetsound)
        mp.setVolume(0.3f,0.3f)
        mp.start()
        mp.setOnCompletionListener{
            mp.release()
        }
    }

    fun backBtn(activity: Activity){        // I1 ( VOLUME INCREASED BY 1 )
        val mp :MediaPlayer = MediaPlayer.create(activity, R.raw.backbtnsound)
        mp.setVolume(0.2f,0.2f)
        mp.start()
        mp.setOnCompletionListener { mp.release() }
    }

    fun mainBtns(activity: Activity){       // I1
        val mp :MediaPlayer = MediaPlayer.create(activity, R.raw.buttonclicked)
        mp.setVolume(0.4f,0.4f)
        mp.start()
        mp.setOnCompletionListener { mp.release() }
    }

    fun resetBtn(activity: Activity){       // I6
        val mp :MediaPlayer = MediaPlayer.create(activity, R.raw.resetbtnmusic)
        mp.setVolume(0.6f,0.6f)
        mp.start()
        mp.setOnCompletionListener { mp.release() }
    }

    fun backBtnAnimated(activity: Activity){        // I1
        val mp :MediaPlayer = MediaPlayer.create(activity, R.raw.backbtnanimation)
        mp.setVolume(0.2f,0.2f)
        mp.start()
        mp.setOnCompletionListener { mp.release() }
    }

    fun boxChecked(activity: Activity){
        val mp :MediaPlayer = MediaPlayer.create(activity, R.raw.boxcheckedsound)
        mp.setVolume(1f,1f)
        mp.start()
        mp.setOnCompletionListener { mp.release() }
    }

    fun replayDialogBox(activity: Activity){     // I1
        val mp :MediaPlayer = MediaPlayer.create(activity, R.raw.replaydialogboxsound)
        mp.setVolume(0.5f,0.5f)
        mp.start()
        mp.setOnCompletionListener { mp.release() }
    }
    fun crossDialogBox(activity: Activity){         // I2
        val mp :MediaPlayer = MediaPlayer.create(activity, R.raw.endgamedialogboxsound)
        mp.setVolume(0.5f,0.5f)
        mp.start()
        mp.setOnCompletionListener { mp.release() }
    }

    fun winnerDialogBoxOpening(activity: Activity){         // I1
        val mp :MediaPlayer = MediaPlayer.create(activity, R.raw.winnningdialogboxopening)
        mp.setVolume(0.5f,0.5f)
        mp.start()
        mp.setOnCompletionListener { mp.release() }
    }
    fun drwaDialogBoxOpening(activity: Activity){         // I1
        val mp :MediaPlayer = MediaPlayer.create(activity, R.raw.drawdialogboxopening1)
        mp.setVolume(0.5f,0.5f)
        mp.start()
        mp.setOnCompletionListener { mp.release() }
    }

//    fun stopMusic(){
//        mediaPlayer?.stop()  // TO STOP ANY PREVIOUSLY PLAYING MUSIC / SOUND
//        mediaPlayer?.release()
//        mediaPlayer = null
//    }

    fun stopMusic(){
        mediaPlayer?.stopAndRelease()
    }

    // Extension function to stop and release MediaPlayer instance
    private fun MediaPlayer?.stopAndRelease() {
        this?.stop()
        this?.release()
    }


}