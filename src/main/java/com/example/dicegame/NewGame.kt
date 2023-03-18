package com.example.dicegame

import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*


@Suppress("NAME_SHADOWING")
class NewGame : AppCompatActivity() {

    //Code Reference for dice animation and media player object  : https://github.com/sandipapps/Simple-Dice-Roller

    private lateinit var playerDiceArray: Array<ImageView>  // Array for players dices
    private lateinit var compDiceArray: Array<ImageView>    // Array for computer dices
    private val random = Random()                           // Random object to randomize dice numbers
    private var delayTime : Int = 20                        // Sleep time for the thread
    private var rollAnimations : Int = 5                    // Number of dice roll animations per throw
    private var plyScore : Int = 0                          // Player Score
    private var compScore : Int = 0                         // Computer Score
    private var playerRandArray = IntArray(5)           // Array to store players random numbers
    private var compRandArray =  IntArray(5)            // Array to store computers random numbers

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide() //Hiding the title bar and action bar.
        setContentView(R.layout.activity_new_game2)

        // Assigning variables to the created buttons.
        val throwButton = findViewById<Button>(R.id.throwbtn)
        val scoreButton = findViewById<Button>(R.id.scorebtn)
        val reRollButton = findViewById<Button>(R.id.rerollbtn)

        val playerTotText = findViewById<TextView>(R.id.plyScoretext)
        val computerTotText = findViewById<TextView>(R.id.compScoreText)

        reRollButton.visibility = View.INVISIBLE
        scoreButton.visibility = View.INVISIBLE

        playerDiceArray = arrayOf( // Array of players dice images
            findViewById(R.id.plydie1),
            findViewById(R.id.plydie2),
            findViewById(R.id.plydie3),
            findViewById(R.id.plydie4),
            findViewById(R.id.plydie5)
        )
        compDiceArray = arrayOf( // Array for the computer's dice images.
            findViewById(R.id.compdie1),
            findViewById(R.id.compdie2),
            findViewById(R.id.compdie3),
            findViewById(R.id.compdie4),
            findViewById(R.id.compdie5)
        )

        val mediaPlayer = MediaPlayer.create(this, R.raw.roll) //Media player object for the sound of dice throw.

        // Methods to be executed on throw button click.
        throwButton.setOnClickListener { try {
            rollDice()
            mediaPlayer.start()
            scoreButton.visibility = View.VISIBLE
            reRollButton.visibility = View.VISIBLE
        } catch (e: Exception) {
            e.printStackTrace()
           }
        }

        // Methods to be executed on score button click.
        scoreButton.setOnClickListener {
            for (i in 0..4){ // loop to iterate through array indexes
                plyScore += playerRandArray[i]
                compScore += compRandArray[i]

                playerTotText.text = plyScore.toString()
                computerTotText.text = compScore.toString()

                scoreButton.visibility = View.INVISIBLE
                reRollButton.visibility = View.INVISIBLE
            }
        }
    }

    // Roll Dice Method to be executed on throw button click.
    private fun rollDice() {
        val runnable = Runnable{ //Defining runnable object for the dice roll animation.
            for (i in 0 until rollAnimations) {
                for (i in playerDiceArray.indices) { // Players dice roll
                    val randomNum = random.nextInt(6) + 1
                    val drawableId = when (randomNum) { // Update the image for the die
                        1 -> R.drawable.dice_one
                        2 -> R.drawable.dice_two
                        3 -> R.drawable.dice_three
                        4 -> R.drawable.dice_four
                        5 -> R.drawable.dice_five
                        else -> R.drawable.dice_six
                    }
                    playerRandArray[i] = randomNum
                    playerDiceArray[i].setImageResource(drawableId) // set the image of the random number rolled

                }
                for (i in compDiceArray.indices) { // Computers dice roll
                    val randomNum = random.nextInt(6) + 1
                    val drawableId = when (randomNum) { // Update the image for the die
                        1 -> R.drawable.dice_one
                        2 -> R.drawable.dice_two
                        3 -> R.drawable.dice_three
                        4 -> R.drawable.dice_four
                        5 -> R.drawable.dice_five
                        else -> R.drawable.dice_six
                    }

                    compRandArray[i] = randomNum
                    compDiceArray[i].setImageResource(drawableId)

                    // In a try block sleep the thread for a smooth animation
                    try {
                        Thread.sleep(delayTime.toLong())
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                }
            }
        }
        val thread = Thread(runnable) // Start the thread. This will cause the run() method to be called where all the dice rolling animation happens.
        thread.start()
    }

}