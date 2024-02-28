package com.example.tictactoe

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.tictactoe.databinding.ActivityPlayersNameBinding
import com.example.tictactoe.isDarkThemeCheck.isDarkMode


class PlayersName : AppCompatActivity() {

    private lateinit var binding: ActivityPlayersNameBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayersNameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(isDarkMode(this)){
            window.statusBarColor=ContextCompat.getColor(this,R.color.grey_statusBarColor)
        }
        else{
            window.statusBarColor=ContextCompat.getColor(this,R.color.white)
        }


        binding.btnNextInPlayersName.setOnClickListener {
            if(binding.editTextPlayer1.text.toString().isNotEmpty() && binding.editTextPlayer2.text.toString().isNotEmpty()){
                startActivity(Intent(this,MainActivity::class.java))
            }
            else{
                Toast.makeText(this, "Please Enter Both the Players Name", Toast.LENGTH_SHORT).show()
            }
        }


        // ALL --- // status bar text color

        // scr1
        //-------

        // scr2
        //-------
        //Play With Computer --- "Left"

        //scr3
        //-----
        //Player's Name should be clear on pressing back button of scr4


        //scr4
        //------
        //Player Box--- "X" ----COLOR CHANGE INITIALLY X IS COMING RED -- BUT SHOULD BE GREEN -- MEANS ON WINNING/DRAW/RESET X TURNS GREEN
        //Player Name should be appear
        //On Winning LINE should appear
        //Dialog box on Winning/Draw


        //  MORE
        //========
        // sound and Animation
        // Players Name
        // dialog box on opening -> restart / close


        // CLEAR TEXT
        // STATUS BAR
        // NAME TRANSFER

    }
    override fun onBackPressed() { // THIS WILL BE IN THE MAIN ACTIVITY

        super.onBackPressed()
        binding.editTextPlayer1.text.clear()
        binding.editTextPlayer2.text.clear()

        Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show()
    }
}