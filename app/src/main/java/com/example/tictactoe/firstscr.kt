package com.example.tictactoe

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.core.content.ContextCompat
import com.example.tictactoe.databinding.ActivityFirstscrBinding
import com.example.tictactoe.databinding.ActivityMainBinding

class firstscr : AppCompatActivity() {
    private lateinit var binding: ActivityFirstscrBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFirstscrBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (isDarkMode(this)) {
            window.statusBarColor = ContextCompat.getColor(this, R.color.grey_statusBarColor);
        }else
            window.statusBarColor=ContextCompat.getColor(this,R.color.white)



        Handler().postDelayed({
            startActivity(Intent(this,team_selection::class.java))
            finish()
        },1500)


    }

    fun isDarkMode(context: Context): Boolean {
        return when (context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> true
            else -> false
        }
    }

}