package com.jackson.tictactoe.ui.gameplay.friend.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jackson.tictactoe.databinding.ActivityPlayWithFriendBinding

class PlayWithFriendActivity : AppCompatActivity() {

    private lateinit var binding : ActivityPlayWithFriendBinding

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
        binding =  ActivityPlayWithFriendBinding.inflate(this.layoutInflater)
        setContentView(binding.root)
        val playerOneName = intent.getStringExtra(INTENT_PARAM_PLAYER_ONE_NAME) ?: "Player One"
        val playerTwoName = intent.getStringExtra(INTENT_PARAM_PLAYER_TWO_NAME) ?: "Player Two"
        binding.llPLayerOne.text = playerOneName + "'s Turn"
        binding.ticTacToeBoard.setUpGame(binding.btnPlayAgain, binding.btnHome, binding.llPLayerOne, arrayOf(playerOneName, playerTwoName))
        setupUI()
    }

    private fun setupUI() {
        binding.btnPlayAgain.setOnClickListener {
            binding.ticTacToeBoard?.resetGame()
        }
    }
}