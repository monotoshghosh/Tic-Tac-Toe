package com.example.tictactoe

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.View
import android.view.Window
import androidx.core.content.ContextCompat

object obj {
    fun vibrate(context: Context, duration: Long = 200) {
        val vib: Vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= 26) {
            vib.vibrate(VibrationEffect.createOneShot(duration, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            vib.vibrate(duration)
        }
    }

//    fun statusBar(){
//        val window:Window =activity.win
//        statusBarColor = ContextCompat.getColor(this@PlayersName, android.R.color.white)
//
//        decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
//
//    }
}