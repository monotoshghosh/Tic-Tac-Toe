package com.monotoshghosh.tictactoe

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.bumptech.glide.Glide
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.monotoshghosh.tictactoe.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var flag = 0
    private var count = 0
    private var winDrawCounter = 0

    private lateinit var player1NameReceiving: String
    private lateinit var player2NameReceiving: String
    private lateinit var dialog: Dialog

    private lateinit var binding: ActivityMainBinding
    private lateinit var gameMode: String

    private var wInterstitialAd1: InterstitialAd? = null
    private var dInterstitialAd2: InterstitialAd? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge() // API 33+

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ✅ Proper edge-to-edge handling
        WindowCompat.setDecorFitsSystemWindows(window, false)

        // Set black background for navigation bar
        window.navigationBarColor = Color.BLACK

        // Make nav and status bar icons light (white)
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightNavigationBars = false
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = false


        applyDisplayCutout()

        MobileAds.initialize(this@MainActivity) {
            loadInterstitialAd1()
            loadInterstitialAd2()
        }

        binding.btnBackMainActivity.setOnClickListener {
            BtnSound.backBtnAnimated(this)
            binding.btnBackMainActivity.isClickable = false
            Glide.with(this).asGif().load(R.drawable.backbtn2gif).into(binding.btnBackMainActivity)
            Handler().postDelayed({ finish() }, 3000)
        }

        binding.btnReset.setOnClickListener {
            val buttons = arrayOf(binding.btn1, binding.btn2, binding.btn3, binding.btn4, binding.btn5, binding.btn6, binding.btn7, binding.btn8, binding.btn9)
            val isButtonPressed = buttons.any { it.text.isNotEmpty() }

            if (isButtonPressed) {
                BtnSound.resetBtn(this)
                Toast.makeText(this, "Game Reset!", Toast.LENGTH_SHORT).show()
                resetGame()
            } else {
                obj.vibrate(this)
                BtnSound.buttonErrorSound(this)
                Toast.makeText(this, "Cannot Reset!! Game Not Started Yet", Toast.LENGTH_SHORT).show()
            }
        }

        player1NameReceiving = intent.getStringExtra("player1_NameToDisplay").toString()
        player2NameReceiving = intent.getStringExtra("player2_NameToDisplay").toString()

        binding.currPlayerNameDisplay.text = player1NameReceiving
        binding.currPlayerNameDisplay.setTextColor(ContextCompat.getColor(this, R.color.red))

        gameMode = intent.getStringExtra("game_mode") ?: "person"
        if (gameMode == "computer") {
            binding.currPlayerNameDisplay.text = "You"
        }
    }

    fun check(view: View) {
        val btnCurrent = view as Button
        count++

        if (btnCurrent.text == "") {
            BtnSound.boxChecked(this)

            if (flag == 0) {
                btnCurrent.text = "X"
                btnCurrent.setTextColor(ContextCompat.getColor(this, R.color.red))

                if (gameMode == "computer") {
                    binding.currPlayerNameDisplay.text = "Computer"
                    binding.currPlayerNameDisplay.setTextColor(ContextCompat.getColor(this, R.color.green2))
                    binding.PlayersTurnBox.text = "O"
                    binding.PlayersTurnBox.setTextColor(ContextCompat.getColor(this, R.color.green))
                    flag = 1

                    if (!checkWin()) {
                        Handler().postDelayed({ computerMove() }, 500)
                    }
                } else {
                    binding.currPlayerNameDisplay.text = player2NameReceiving
                    binding.currPlayerNameDisplay.setTextColor(ContextCompat.getColor(this, R.color.green2))
                    binding.PlayersTurnBox.text = "O"
                    binding.PlayersTurnBox.setTextColor(ContextCompat.getColor(this, R.color.green))
                    flag = 1
                    checkWin()
                }
            } else {
                btnCurrent.text = "O"
                btnCurrent.setTextColor(ContextCompat.getColor(this, R.color.green))
                binding.currPlayerNameDisplay.text = player1NameReceiving
                binding.currPlayerNameDisplay.setTextColor(ContextCompat.getColor(this, R.color.red))
                binding.PlayersTurnBox.text = "X"
                binding.PlayersTurnBox.setTextColor(ContextCompat.getColor(this, R.color.red))
                flag = 0
                checkWin()
            }
        }
    }

    private fun computerMove() {
        val buttons = arrayOf(binding.btn1, binding.btn2, binding.btn3, binding.btn4, binding.btn5, binding.btn6, binding.btn7, binding.btn8, binding.btn9)
        val emptyButtons = buttons.filter { it.text == "" }

        if (emptyButtons.isNotEmpty()) {
            val button = emptyButtons.random()
            button.text = "O"
            BtnSound.boxChecked(this)
            button.setTextColor(ContextCompat.getColor(this, R.color.green))
            binding.currPlayerNameDisplay.text = "You"
            binding.currPlayerNameDisplay.setTextColor(ContextCompat.getColor(this, R.color.red))
            binding.PlayersTurnBox.text = "X"
            binding.PlayersTurnBox.setTextColor(ContextCompat.getColor(this, R.color.red))
            flag = 0
            count++
            checkWin()
        }
    }

    private fun checkWin(): Boolean {
        val b = arrayOf(
            binding.btn1.text.toString(), binding.btn2.text.toString(), binding.btn3.text.toString(),
            binding.btn4.text.toString(), binding.btn5.text.toString(), binding.btn6.text.toString(),
            binding.btn7.text.toString(), binding.btn8.text.toString(), binding.btn9.text.toString()
        )

        val winLines = arrayOf(
            Triple(0, 1, 2), Triple(3, 4, 5), Triple(6, 7, 8),
            Triple(0, 3, 6), Triple(1, 4, 7), Triple(2, 5, 8),
            Triple(0, 4, 8), Triple(2, 4, 6)
        )

        for ((i, j, k) in winLines) {
            if (b[i] == b[j] && b[j] == b[k] && b[i].isNotEmpty()) {
                val winner = if (flag == 1) "X" else "O"
                val winningPlayer = if (winner == "X") player1NameReceiving else player2NameReceiving
                Toast.makeText(this, "Winner $winner", Toast.LENGTH_SHORT).show()

                winDrawCounter++

                obj.winnerDialog(this, {
                    if (winDrawCounter % 3 == 0) wInterstitialAd1?.show(this)
                    resetGame()
                }, winningPlayer, winner)
                return true
            }
        }

        if (count == 9) {
            Toast.makeText(this, "DRAW", Toast.LENGTH_SHORT).show()
            winDrawCounter++
            obj.drawDialogBox(this) {
                if (winDrawCounter % 3 == 0) dInterstitialAd2?.show(this)
                resetGame()
            }
            return true
        }

        return false
    }

    private fun resetGame() {
        val buttons = arrayOf(binding.btn1, binding.btn2, binding.btn3, binding.btn4, binding.btn5, binding.btn6, binding.btn7, binding.btn8, binding.btn9)
        for (btn in buttons) btn.text = ""
        flag = 0
        count = 0
        binding.PlayersTurnBox.text = "X"
        binding.PlayersTurnBox.setTextColor(ContextCompat.getColor(this, R.color.red))
        binding.currPlayerNameDisplay.text = player1NameReceiving
        binding.currPlayerNameDisplay.setTextColor(ContextCompat.getColor(this, R.color.red))

        loadInterstitialAd1()
        loadInterstitialAd2()
    }

    private fun loadInterstitialAd1() {
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(this, "ca-app-pub-8334546624219108/2228115342", adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                wInterstitialAd1 = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                wInterstitialAd1 = interstitialAd
            }
        })
    }

    private fun loadInterstitialAd2() {
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(this, "ca-app-pub-8334546624219108/3472597025", adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                dInterstitialAd2 = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                dInterstitialAd2 = interstitialAd
            }
        })
    }

    private fun applyDisplayCutout() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.mainActivityConstLayout) { view, insets ->
            val systemBars = insets.getInsets(
                WindowInsetsCompat.Type.systemBars() or
                        WindowInsetsCompat.Type.displayCutout()
            )
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            WindowInsetsCompat.CONSUMED
        }
    }
}
