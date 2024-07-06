package com.monotoshghosh.tictactoe

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.media.MediaPlayer
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide

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

        BtnSound.winnerDialogBoxOpening(context as Activity)  // OPENING SOUND

        val WinningImage = dialog.findViewById<ImageView>(R.id.winnerTrophyImg)
        Glide.with(context).asGif().load(R.drawable.winnertrophy1).into(WinningImage)

        val cele1Val =dialog.findViewById<ImageView>(R.id.celeID1)
        Glide.with(context).asGif().load(R.drawable.cele1).into(cele1Val)

        val cele2Val =dialog.findViewById<ImageView>(R.id.celeID2)
        Glide.with(context).asGif().load(R.drawable.cele2).into(cele2Val)




        val WinnerName =dialog.findViewById<TextView>(R.id.winnerName)
        WinnerName.text = winnerName

        if(symbol=="X"){
            WinnerName.setTextColor(ContextCompat.getColor(context, R.color.red))
        }
        else{
            WinnerName.setTextColor(ContextCompat.getColor(context, R.color.green2))

        }


        val replayBtn = dialog.findViewById<Button>(R.id.replay_Btn_dialog_draw)
        replayBtn.setOnClickListener {
            resetGame()
            BtnSound.stopMusic()            // TO STOP PREVIOUSLY PLAYING MUSIC
            BtnSound.replayDialogBox(context as Activity)
            dialog.dismiss()
        }

        val exitBtn = dialog.findViewById<Button>(R.id.cancel_dialog_winner)
        exitBtn.setOnClickListener {
            BtnSound.stopMusic()
            BtnSound.crossDialogBox(context as Activity)
            (context as Activity).finishAffinity()
        }
    }

    fun checkPlayerName(symbol:String,Person1Name:String,Person2Name:String): String {
        return if(symbol == "X")
            Person1Name
        else
            Person2Name
    }

    fun drawDialogBox(context: Context,resetGame: () -> Unit){
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.cust_layout_dialog_box_draw)
        dialog.window?.setBackgroundDrawableResource(R.drawable.dialog_box_shape)
        dialog.setCancelable(false)
        dialog.show()

        BtnSound.drwaDialogBoxOpening(context as Activity)   // DRAW...DIALOG BOX OPENING SOUND

        val drawDialogBoxEndGame = dialog.findViewById<ImageView>(R.id.drawDialogBoxEndGame)
        drawDialogBoxEndGame.setOnClickListener {
            BtnSound.stopMusic()                           // TO STOP THE PREVIOUSLY PLAYING SOUND ----> drawDialogBoxOpening(...)
            BtnSound.crossDialogBox(context as Activity)
            (context as Activity).finishAffinity()
        }

        val replay_DrawDialogBox = dialog.findViewById<Button>(R.id.replay_Btn_dialog_draw)
        replay_DrawDialogBox.setOnClickListener {
            resetGame()
            BtnSound.stopMusic()
            BtnSound.replayDialogBox(context as Activity)
            dialog.dismiss()
        }

    }

}

//    fun statusBar(){
//        val window:Window =activity.win
//        statusBarColor = ContextCompat.getColor(this@PlayersName, android.R.color.white)
//
//        decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
//
//    }
