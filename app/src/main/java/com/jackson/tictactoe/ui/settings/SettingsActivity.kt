package com.jackson.tictactoe.ui.settings

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jackson.tictactoe.R

class SettingsActivity : AppCompatActivity() {

    companion object {
        fun startActivity(context: Context) {
            context.startActivity(
                Intent(context, SettingsActivity::class.java)
            )
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
    }
}