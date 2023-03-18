package com.example.dicegame


import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    @SuppressLint("InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide() //Hiding the title bar and action bar.
        setContentView(R.layout.activity_main)

        val aboutUsBtn = findViewById<Button>(R.id.aboutusbtn) //Creating the variable for about us button.

        aboutUsBtn.setOnClickListener { //Method to execute the about us button and pop up the dialog box.

            val dialogBinding = layoutInflater.inflate(R.layout.about_us_dialog,null)
            val dialogBox = Dialog(this)

            dialogBox.setContentView(dialogBinding)
            dialogBox.setCancelable(true)
            dialogBox.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialogBox.show()

            val okBtn = dialogBinding.findViewById<Button>(R.id.okBtn) // OK button to closes the About us dialog box.
            okBtn.setOnClickListener {
                dialogBox.dismiss()
            }
        }

        val newGameBtn = findViewById<Button>(R.id.newgamebtn)//Creating the variable for new game button.

        newGameBtn.setOnClickListener { //Method to execute New Game button and start game.
           val intent = Intent(this,NewGame::class.java)
            startActivity(intent)
            val mediaPlayer = MediaPlayer.create(this, R.raw.bgmusic) // Media player object to play the background music on the new game screen.
            mediaPlayer.start()
        }

    }
}