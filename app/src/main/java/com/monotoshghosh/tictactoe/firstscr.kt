package com.monotoshghosh.tictactoe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.monotoshghosh.tictactoe.databinding.ActivityFirstscrBinding

class firstscr : AppCompatActivity() {
    private lateinit var binding: ActivityFirstscrBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()                                       // API 35
        super.onCreate(savedInstanceState)
        binding = ActivityFirstscrBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Apply display cutout handling
        applyDisplayCutout()                                     // API 35

        // HIDE NAVIGATION BAR AND STATUS BAR
        hideSystemBar()

        // Deprecated Code
//        window.apply {    // SETTING THE STATUS BAR
//            statusBarColor=ContextCompat.getColor(this@firstscr, R.color.white) // BACKGROUND -> WHITE
//            decorView.systemUiVisibility= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR        // ICONS -> DARK
//        }

        // FOR BACKGROUND MUSIC -- > START
        // CALLING THE FUNCTION
//        startMusic()

        Handler().postDelayed({       // SHIFTING TO NEW ACTIVITY -> AFTER FEW SECONDS
            startActivity(Intent(this, team_selection::class.java))
            finish()
        },1500)

    }

    // FUNCTION FOR BACKGROUND MUSIC
    private fun startMusic() {
        startService(Intent(this, MusicService::class.java))
    }
    private fun applyDisplayCutout(){
        ViewCompat.setOnApplyWindowInsetsListener(binding.firstScrConstLayout) { view, insets ->
            val systemBars = insets.getInsets(
                WindowInsetsCompat.Type.systemBars() or
                        WindowInsetsCompat.Type.displayCutout()
            )
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            WindowInsetsCompat.CONSUMED
        }

    }

    private fun hideSystemBar() {
        WindowCompat.setDecorFitsSystemWindows(window, false)

        val controller = WindowInsetsControllerCompat(window, window.decorView)
        controller.hide(
            WindowInsetsCompat.Type.statusBars() or
                    WindowInsetsCompat.Type.navigationBars()
        )
        controller.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
    }


}