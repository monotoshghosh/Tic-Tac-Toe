package com.example.tictactoe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.core.content.ContextCompat
import com.example.tictactoe.databinding.ActivityFirstscrBinding

class firstscr : AppCompatActivity() {
    private lateinit var binding: ActivityFirstscrBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFirstscrBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.apply {    // SETTING THE STATUS BAR
            statusBarColor=ContextCompat.getColor(this@firstscr,R.color.white) // BACKGROUND -> WHITE
            decorView.systemUiVisibility= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR        // ICONS -> DARK
        }

        Handler().postDelayed({       // SHIFTING TO NEW ACTIVITY -> AFTER FEW SECONDS
            startActivity(Intent(this,team_selection::class.java))
            finish()
        },1500)

    }

}