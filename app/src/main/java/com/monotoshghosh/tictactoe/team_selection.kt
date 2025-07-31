package com.monotoshghosh.tictactoe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.monotoshghosh.tictactoe.databinding.ActivityTeamSelectionBinding

class team_selection : AppCompatActivity() {
    private lateinit var binding: ActivityTeamSelectionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        binding = ActivityTeamSelectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        applyDisplayCutout()


        window.statusBarColor = ContextCompat.getColor(this, R.color.white)  // SAME AS DID IN THE firstscr.kt
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

//        hideNavigationBar()

        binding.btnBackTeamSelection.setOnClickListener { // EXIT THE GAME ON CLICKING THE BACK_ICON
            BtnSound.backBtn(this)
            finish()
        }
        
        binding.playPerson.setOnClickListener {             //   ACTION ON CLICKING THE PLAY WITH PERSON
            BtnSound.mainBtns(this)
            startActivity(Intent(this, PlayersName::class.java))
        }
        binding.playComputer.setOnClickListener {            //   ACTION ON CLICKING THE PLAY WITH COMPUTER
//            obj.vibrate(this)
//            BtnSound.buttonErrorSound(this)
//            Toast.makeText(this, "I am still Working", Toast.LENGTH_SHORT).show()

            BtnSound.mainBtns(this)
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("player1_NameToDisplay", "You")
            intent.putExtra("player2_NameToDisplay", "Computer")
            intent.putExtra("game_mode", "computer")
            startActivity(intent)

        }

        
    }

    private fun hideNavigationBar() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val controller = WindowInsetsControllerCompat(window, window.decorView)
        controller.hide(WindowInsetsCompat.Type.navigationBars())
        controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
    }

    private fun applyDisplayCutout(){
        ViewCompat.setOnApplyWindowInsetsListener(binding.teamSelConsLayout) { view, insets ->
            val systemBars = insets.getInsets(
                WindowInsetsCompat.Type.systemBars() or
                        WindowInsetsCompat.Type.displayCutout()
            )
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            WindowInsetsCompat.CONSUMED
        }

    }

}