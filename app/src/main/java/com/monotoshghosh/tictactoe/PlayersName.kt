package com.monotoshghosh.tictactoe

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.monotoshghosh.tictactoe.databinding.ActivityPlayersNameBinding

class PlayersName : AppCompatActivity() {

    private lateinit var binding: ActivityPlayersNameBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()                                       // API 35
        super.onCreate(savedInstanceState)

        binding = ActivityPlayersNameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Apply display cutout handling
        applyDisplayCutout()                                     // API 35

        // Set status bar color and dark icons
        window.statusBarColor = ContextCompat.getColor(this, R.color.white)
        WindowCompat.setDecorFitsSystemWindows(window, true)
        val insetsController = WindowInsetsControllerCompat(window, window.decorView)
        insetsController.isAppearanceLightStatusBars = true  // Makes status bar icons dark


//        hideNavigationBar()

        window.apply {
            // SET THE STATUS BAR COLOR TO WHITE
            statusBarColor = ContextCompat.getColor(this@PlayersName, android.R.color.white)

            // SET THE STATUS BAR ICONS TO BE DARK
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        }



        binding.btnBackPlayersname.setOnClickListener {  // TO MOVE TO TEAM SELECTION ACTIVITY
            BtnSound.backBtn(this)
            finish()
        }


        binding.btnNextInPlayersName.setOnClickListener {
            if(binding.editTextPlayer1.text.toString().isNotEmpty() && binding.editTextPlayer2.text.toString().isNotEmpty()){

                BtnSound.mainBtns(this)

                intent =Intent(this@PlayersName, MainActivity::class.java)
                val player1Name =binding.editTextPlayer1.text.toString()  // PASSING THE PLAYERS NAME
                val player2Name =binding.editTextPlayer2.text.toString()
                intent.putExtra("player1_NameToDisplay",player1Name)
                intent.putExtra("player2_NameToDisplay",player2Name)
                startActivity(intent)

                Handler().postDelayed({                         // CLEARING THE PLAYERS NAME AFTER CLICKING NEXT..
                    binding.editTextPlayer1.text.clear()
                    binding.editTextPlayer2.text.clear()},
                    200)

            }
            else{                                              // IF FIELD IS EMPTY THEN VIBRATE AND TOAST
                BtnSound.buttonErrorSound(this)
                obj.vibrate(this)
                Toast.makeText(this, "Please enter both the Players name", Toast.LENGTH_SHORT).show()
            }
        }


        // keyboard button --- sound and vibrate

        // scr2
        //-------
        //Play With Computer --- "Left"


        //MAIN ACTIVITY SCREEN
        //------

        // on opening say game start sound
        //On Winning LINE should appear
        // BOX CLICKING SOUND


        // REMOVE ALL THE RESET() OF WINNING

    }

    override fun onDestroy() {
        Log.d("PlayersName", "onDestroy called")

        stopService(Intent(this, MusicService::class.java))
        super.onDestroy()
    }
    override fun onBackPressed() { // THIS WILL BE IN THE MAIN ACTIVITY

        super.onBackPressed() // FOR DEFAULT WORK OF BACK BUTTON
        Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show()
    }

    private fun hideNavigationBar() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val controller = WindowInsetsControllerCompat(window, window.decorView)
        controller.hide(WindowInsetsCompat.Type.navigationBars())
        controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
    }

    private fun applyDisplayCutout(){
        ViewCompat.setOnApplyWindowInsetsListener(binding.playersNameConstLayout) { view, insets ->
            val systemBars = insets.getInsets(
                WindowInsetsCompat.Type.systemBars() or
                        WindowInsetsCompat.Type.displayCutout()
            )
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            WindowInsetsCompat.CONSUMED
        }

    }


}