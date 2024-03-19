package com.example.tictactoe

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.tictactoe.databinding.ActivityMainBinding
import com.bumptech.glide.Glide


class MainActivity : AppCompatActivity() {

    var flag =0      // WHICH PLAYER'S TURN
    var count =0     // IT WILL CHECK WHEN ALL THE BOXs ARE PRESSED

    lateinit var player1NameReceiving: String
    lateinit var player2NameReceiving : String

    lateinit var dialog: Dialog

    private lateinit var binding: ActivityMainBinding  // ALWAYS INITIALIZE SHOULD BE < lateinit var & outside onCreate >
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor =ContextCompat.getColor(this,R.color.MainScreenColor)
        window.decorView.systemUiVisibility = 0

        // FOR DIALOG BOX
        dialog = Dialog(this)
        dialog.setContentView(R.layout.cust_layout_dialog_box_winner)
        dialog.window?.setBackgroundDrawableResource(R.drawable.dialog_box_shape)
//        dialog.setCancelable(false)        // TO PREVENT THE DIALOG FROM CLOSING (TOUCHING OUTSIDE/BACK BUTTON)

        binding.buttontest.setOnClickListener {
            dialog.show()

            val replayBtn = dialog.findViewById<Button>(R.id.replay_Btn_dialog_draw)
            replayBtn.setOnClickListener {
                resetGame()
                dialog.dismiss()
            }

            val exitBtn = dialog.findViewById<Button>(R.id.cancel_dialog_winner)
            exitBtn.setOnClickListener {
                finishAffinity()
            }
        }

        binding.drawTest.setOnClickListener {
            obj.drawDialogBox(this,::resetGame)
        }

        // FOR ANIMATED BACKGROUND
        val backBtnGif= binding.btnBackMainActivity
        binding.btnBackMainActivity.setOnClickListener {
            Glide.with(this).asGif().load(R.drawable.backbtn2gif).into(backBtnGif) // ANIMATED BACK BUTTON

            Handler().postDelayed({ finish()}, 3000)
        }


        binding.btnReset.setOnClickListener {
            resetGame()
        }

        player1NameReceiving = intent.getStringExtra("player1_NameToDisplay").toString()   // ALWAYS DECLARE THE getExtra inside onCreate()..
        player2NameReceiving = intent.getStringExtra("player2_NameToDisplay").toString()

        binding.currPlayerNameDisplay.text=player1NameReceiving     // ININTIALLY THE currPlayerNameDisplay IS EMPTY
        binding.currPlayerNameDisplay.setTextColor(ContextCompat.getColor(this,R.color.red))

    }

    fun check(view: View) {

        val btnCurrent = view as Button
        count++

        if (btnCurrent.text == "") {        // CHECK IF BOX IS ALREADY PRESSED

            if (flag == 0) {                // PLAYER ONE TURN (X)
                btnCurrent.text = "X"
                btnCurrent.setTextColor(ContextCompat.getColor(this,R.color.red))

                binding.currPlayerNameDisplay.text = player2NameReceiving
                binding.currPlayerNameDisplay.setTextColor(ContextCompat.getColor(this,R.color.green2))

                binding.PlayersTurnBox.text="O"
                binding.PlayersTurnBox.setTextColor(ContextCompat.getColor(this,R.color.green))

                flag = 1

            } else {                        // PLAYER TWO TURN (O)
                btnCurrent.text = "O"
                btnCurrent.setTextColor(ContextCompat.getColor(this,R.color.green))

                binding.currPlayerNameDisplay.text = player1NameReceiving
                binding.currPlayerNameDisplay.setTextColor(ContextCompat.getColor(this,R.color.red))

                binding.PlayersTurnBox.text="X"
                binding.PlayersTurnBox.setTextColor(ContextCompat.getColor(this,R.color.red))

                flag = 0
            }

            val b1 = binding.btn1.text.toString()  // STORING THE BUTTON RESPONSE
            val b2 = binding.btn2.text.toString()
            val b3 = binding.btn3.text.toString()
            val b4 = binding.btn4.text.toString()
            val b5 = binding.btn5.text.toString()
            val b6 = binding.btn6.text.toString()
            val b7 = binding.btn7.text.toString()
            val b8 = binding.btn8.text.toString()
            val b9 = binding.btn9.text.toString()

            // CHECKING WINNING CONDITION

            if(b1==b2 && b2==b3 && b3!=""){
                Toast.makeText(this, "Winner $b1", Toast.LENGTH_SHORT).show()
                resetGame()

                val winningPlayer =obj.checkPlayerName(b1,player1NameReceiving,player2NameReceiving) // FUNCTION CALL TO CHECK WHICH PLAYER WON( NAME )
                obj.winnerDialog(this, ::resetGame,winningPlayer,b1)  // FUNCTION TO CALL DIALOG BOX
            }
            else if(b4==b5 && b5==b6 && b6!=""){
                Toast.makeText(this, "Winner $b4", Toast.LENGTH_SHORT).show()
                resetGame()

                val winningPlayer =obj.checkPlayerName(b1,player1NameReceiving,player2NameReceiving)
                obj.winnerDialog(this, ::resetGame,winningPlayer,b1)

            }
            else if(b7==b8 && b8==b9 && b9!=""){
                Toast.makeText(this, "Winner $b7", Toast.LENGTH_SHORT).show()
                resetGame()

                val winningPlayer =obj.checkPlayerName(b1,player1NameReceiving,player2NameReceiving)
                obj.winnerDialog(this, ::resetGame,winningPlayer,b1)

            }
            else if(b1==b4 && b4==b7 && b7!=""){
                Toast.makeText(this, "Winner $b1", Toast.LENGTH_SHORT).show()
                resetGame()

                val winningPlayer =obj.checkPlayerName(b1,player1NameReceiving,player2NameReceiving)
                obj.winnerDialog(this, ::resetGame,winningPlayer,b1)

            }
            else if(b2==b5 && b5==b8 && b8!=""){
                Toast.makeText(this, "Winner $b2", Toast.LENGTH_SHORT).show()
                resetGame()

                val winningPlayer =obj.checkPlayerName(b1,player1NameReceiving,player2NameReceiving)
                obj.winnerDialog(this, ::resetGame,winningPlayer,b1)

            }
            else if(b3==b6 && b6==b9 && b9!=""){
                Toast.makeText(this, "Winner $b3", Toast.LENGTH_SHORT).show()
                resetGame()

                val winningPlayer =obj.checkPlayerName(b1,player1NameReceiving,player2NameReceiving)
                obj.winnerDialog(this, ::resetGame,winningPlayer,b1)

            }
            else if(b1==b5 && b5==b9 && b9!=""){
                Toast.makeText(this, "Winner $b1", Toast.LENGTH_SHORT).show()
                resetGame()

                val winningPlayer =obj.checkPlayerName(b1,player1NameReceiving,player2NameReceiving)
                obj.winnerDialog(this, ::resetGame,winningPlayer,b1)

            }
            else if(b3==b5 && b5==b7 && b7!=""){
                Toast.makeText(this, "Winner $b3", Toast.LENGTH_SHORT).show()
                resetGame()

                val winningPlayer =obj.checkPlayerName(b1,player1NameReceiving,player2NameReceiving)
                obj.winnerDialog(this, ::resetGame,winningPlayer,b1)

            }
            else if(count==9){
                Toast.makeText(this, "DRAW", Toast.LENGTH_SHORT).show()
                resetGame()

                obj.drawDialogBox(this,::resetGame)

                // DRAW FUNCTION TO BE CALLED HERE.......
            }
        }
    }
    fun resetGame(){
        BtnSound.buttonResetSound(this)
        binding.btn1.text=""
        binding.btn2.text=""
        binding.btn3.text=""
        binding.btn4.text=""
        binding.btn5.text=""
        binding.btn6.text=""
        binding.btn7.text=""
        binding.btn8.text=""
        binding.btn9.text=""

        flag=0
        count =0
        binding.PlayersTurnBox.text="X"
        binding.PlayersTurnBox.setTextColor(ContextCompat.getColor(this,R.color.red))
        binding.currPlayerNameDisplay.text =player1NameReceiving
        binding.currPlayerNameDisplay.setTextColor(ContextCompat.getColor(this,R.color.red))

    }

}
