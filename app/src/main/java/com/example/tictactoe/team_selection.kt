package com.example.tictactoe

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.tictactoe.databinding.ActivityTeamSelectionBinding

class team_selection : AppCompatActivity() {
    private lateinit var binding: ActivityTeamSelectionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTeamSelectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (isDarkThemeCheck.isDarkMode(this)) {
            window.statusBarColor = ContextCompat.getColor(this, R.color.grey_statusBarColor);
        }
        else {
            window.statusBarColor = ContextCompat.getColor(this, R.color.white)
        }
        
        binding.playPerson.setOnClickListener { 
            startActivity(Intent(this,PlayersName::class.java))
        }
        binding.playComputer.setOnClickListener {
            Toast.makeText(this, "I am still Working", Toast.LENGTH_SHORT).show()
        }

        
    }

}