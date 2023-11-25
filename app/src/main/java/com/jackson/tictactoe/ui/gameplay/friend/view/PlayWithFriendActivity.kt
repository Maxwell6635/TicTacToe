package com.jackson.tictactoe.ui.gameplay.friend.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jackson.tictactoe.R

class PlayWithFriendActivity : AppCompatActivity() {

    companion object {
        private const val INTENT_PARAM_PLAYER_ONE_NAME = "INTENT_PARAM_PLAYER_ONE_NAME"
        private const val INTENT_PARAM_PLAYER_TWO_NAME = "INTENT_PARAM_PLAYER_TWO_NAME"

        fun startActivity(context: Context, playerOneName: String, playerTwoName: String) {
            context.startActivity(
                Intent(context, PlayWithFriendActivity::class.java)
                    .putExtra(INTENT_PARAM_PLAYER_ONE_NAME, playerOneName)
                    .putExtra(INTENT_PARAM_PLAYER_TWO_NAME, playerTwoName)
            )
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_with_friend)
    }
}