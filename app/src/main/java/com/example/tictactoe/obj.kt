package com.example.tictactoe

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat

object obj {
    fun vibrate(context: Context, duration: Long = 200) {           // FUNC FOR VIBRATION
        val vib: Vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= 26) {
            vib.vibrate(VibrationEffect.createOneShot(duration, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            vib.vibrate(duration)
        }
    }
    fun winnerDialog(context: Context,resetGame:()->Unit,winnerName:String,symbol: String){ // FUNC FOR WINNER DIALOG BOX

        val dialog = Dialog(context)
        dialog.setContentView(R.layout.cust_layout_dialog_box_winner)
        dialog.window?.setBackgroundDrawableResource(R.drawable.dialog_box_shape)
        dialog.setCancelable(false)        // TO PREVENT THE DIALOG FROM CLOSING (TOUCHING OUTSIDE/BACK BUTTON)
        dialog.show()


        val WinnerName =dialog.findViewById<TextView>(R.id.winnerName)
        WinnerName.text = winnerName

        if(symbol=="X"){
            WinnerName.setTextColor(ContextCompat.getColor(context,R.color.red))
        }
        else{
            WinnerName.setTextColor(ContextCompat.getColor(context,R.color.green2))

        }


        val replayBtn = dialog.findViewById<Button>(R.id.replay_dialog_winner)
        replayBtn.setOnClickListener {
            resetGame()
            dialog.dismiss()
        }

        val exitBtn = dialog.findViewById<Button>(R.id.cancel_dialog_winner)
        exitBtn.setOnClickListener {
            (context as Activity).finishAffinity()
        }
    }

    fun checkPlayerName(symbol:String,Person1Name:String,Person2Name:String): String {
        return if(symbol == "X")
            Person1Name
        else
            Person2Name
    }

}

//    fun statusBar(){
//        val window:Window =activity.win
//        statusBarColor = ContextCompat.getColor(this@PlayersName, android.R.color.white)
//
//        decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
//
//    }
