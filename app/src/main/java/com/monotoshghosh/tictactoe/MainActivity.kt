package com.monotoshghosh.tictactoe

import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.monotoshghosh.tictactoe.databinding.ActivityMainBinding
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

class MainActivity : AppCompatActivity() {

    private var flag = 0 // WHICH PLAYER'S TURN
    private var count = 0 // IT WILL CHECK WHEN ALL THE BOXes ARE PRESSED

    private lateinit var player1NameReceiving: String
    private lateinit var player2NameReceiving: String
    private lateinit var dialog: Dialog

    private lateinit var binding: ActivityMainBinding // ALWAYS INITIALIZE SHOULD BE < lateinit var & outside onCreate >

    private lateinit var gameMode: String // Variable to store the game mode

    private var wInterstitialAd1: InterstitialAd? = null
    private var dInterstitialAd2: InterstitialAd? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = ContextCompat.getColor(this, R.color.MainScreenColor)
        window.decorView.systemUiVisibility = 0

        MobileAds.initialize(this@MainActivity) {}
        loadinterstitialAd1()
        loadinterstitialAd2()



        // FOR ANIMATED BACKGROUND
        val backBtnGif = binding.btnBackMainActivity
        binding.btnBackMainActivity.setOnClickListener {
            BtnSound.backBtnAnimated(this)
            backBtnGif.isClickable = false // TO DISABLE MULTIPLE CLICK
            Glide.with(this).asGif().load(R.drawable.backbtn2gif).into(backBtnGif) // ANIMATED BACK BUTTON

            Handler().postDelayed({ finish() }, 3000)
        }

        binding.btnReset.setOnClickListener {
            val buttons = arrayOf(binding.btn1, binding.btn2, binding.btn3, binding.btn4, binding.btn5, binding.btn6, binding.btn7, binding.btn8, binding.btn9)
            val isButtonPressed: Boolean = buttons.any { it.text.isNotEmpty() }

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

        player1NameReceiving = intent.getStringExtra("player1_NameToDisplay").toString() // ALWAYS DECLARE THE getExtra inside onCreate()..
        player2NameReceiving = intent.getStringExtra("player2_NameToDisplay").toString()

        binding.currPlayerNameDisplay.text = player1NameReceiving // INITIALLY THE currPlayerNameDisplay IS EMPTY
        binding.currPlayerNameDisplay.setTextColor(ContextCompat.getColor(this, R.color.red))

        // Set the game mode
        gameMode = intent.getStringExtra("game_mode") ?: "person"
        if (gameMode == "computer") {
            binding.currPlayerNameDisplay.text = "You"
        }
    }

    fun check(view: View) {
        val btnCurrent = view as Button
        count++

        if (btnCurrent.text == "") { // CHECK IF BOX IS ALREADY PRESSED
            BtnSound.boxChecked(this)

            if (flag == 0) { // PLAYER ONE TURN (X)
                btnCurrent.text = "X"
                btnCurrent.setTextColor(ContextCompat.getColor(this, R.color.red))

                if (gameMode == "computer") {
                    binding.currPlayerNameDisplay.text = "Computer"
                    binding.currPlayerNameDisplay.setTextColor(ContextCompat.getColor(this,
                        R.color.green2
                    ))

                    binding.PlayersTurnBox.text = "O"
                    binding.PlayersTurnBox.setTextColor(ContextCompat.getColor(this, R.color.green))

                    flag = 1

                    // Check for win or draw after player move
                    if (!checkWin()) {
                        Handler().postDelayed({ computerMove() }, 500)
                    }
                } else { // Play with Person
                    binding.currPlayerNameDisplay.text = player2NameReceiving
                    binding.currPlayerNameDisplay.setTextColor(ContextCompat.getColor(this,
                        R.color.green2
                    ))

                    binding.PlayersTurnBox.text = "O"
                    binding.PlayersTurnBox.setTextColor(ContextCompat.getColor(this, R.color.green))

                    flag = 1

                    // Check for win or draw after player move
                    checkWin()
                }
            } else { // PLAYER TWO TURN (O)
                btnCurrent.text = "O"
                btnCurrent.setTextColor(ContextCompat.getColor(this, R.color.green))

                binding.currPlayerNameDisplay.text = player1NameReceiving
                binding.currPlayerNameDisplay.setTextColor(ContextCompat.getColor(this, R.color.red))

                binding.PlayersTurnBox.text = "X"
                binding.PlayersTurnBox.setTextColor(ContextCompat.getColor(this, R.color.red))

                flag = 0

                // Check for win or draw after player move
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

            // Check for win or draw after computer move
            checkWin()
        }
    }

    private fun checkWin(): Boolean {
        val b1 = binding.btn1.text.toString()
        val b2 = binding.btn2.text.toString()
        val b3 = binding.btn3.text.toString()
        val b4 = binding.btn4.text.toString()
        val b5 = binding.btn5.text.toString()
        val b6 = binding.btn6.text.toString()
        val b7 = binding.btn7.text.toString()
        val b8 = binding.btn8.text.toString()
        val b9 = binding.btn9.text.toString()

        // Winning conditions
        if ((b1 == b2 && b2 == b3 && b3 != "") ||
            (b4 == b5 && b5 == b6 && b6 != "") ||
            (b7 == b8 && b8 == b9 && b9 != "") ||
            (b1 == b4 && b4 == b7 && b7 != "") ||
            (b2 == b5 && b5 == b8 && b8 != "") ||
            (b3 == b6 && b6 == b9 && b9 != "") ||
            (b1 == b5 && b5 == b9 && b9 != "") ||
            (b3 == b5 && b5 == b7 && b7 != "")) {
            val winner = if (flag == 1) "X" else "O"
            val winningPlayer = if (winner == "X") player1NameReceiving else player2NameReceiving
            Toast.makeText(this, "Winner $winner", Toast.LENGTH_SHORT).show()
            obj.winnerDialog(this, {
                wInterstitialAd1?.show(this)
                resetGame()
            }, winningPlayer, winner)
            return true
        }

        if (count == 9) {
            Toast.makeText(this, "DRAW", Toast.LENGTH_SHORT).show()
            obj.drawDialogBox(this) {
                dInterstitialAd2?.show(this)
                resetGame()
            }

            return true
        }

        return false
    }

    private fun resetGame() {
        binding.btn1.text = ""
        binding.btn2.text = ""
        binding.btn3.text = ""
        binding.btn4.text = ""
        binding.btn5.text = ""
        binding.btn6.text = ""
        binding.btn7.text = ""
        binding.btn8.text = ""
        binding.btn9.text = ""

        flag = 0
        count = 0
        binding.PlayersTurnBox.text = "X"
        binding.PlayersTurnBox.setTextColor(ContextCompat.getColor(this, R.color.red))
        binding.currPlayerNameDisplay.text = player1NameReceiving
        binding.currPlayerNameDisplay.setTextColor(ContextCompat.getColor(this, R.color.red))

        loadinterstitialAd1()
        loadinterstitialAd2()

    }

    // FOR WINNER
    fun loadinterstitialAd1(){
        var adRequest = AdRequest.Builder().build()
        InterstitialAd.load(this,"ca-app-pub-8334546624219108/2228115342", adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                wInterstitialAd1 = null
            }
            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                wInterstitialAd1 = interstitialAd
            }
        })
    }

    // FOR DRAW
    fun loadinterstitialAd2(){
        var adRequest = AdRequest.Builder().build()
        InterstitialAd.load(this,"ca-app-pub-8334546624219108/3472597025", adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                dInterstitialAd2 = null
            }
            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                dInterstitialAd2 = interstitialAd
            }
        })
    }
}

