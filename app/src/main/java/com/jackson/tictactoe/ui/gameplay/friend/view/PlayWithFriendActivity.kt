package com.jackson.tictactoe.ui.gameplay.friend.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.jackson.tictactoe.R
import com.jackson.tictactoe.databinding.ActivityPlayWithFriendBinding
import com.jackson.tictactoe.ui.gameplay.friend.viewmodel.PlayWithFriendViewModel
import com.jackson.tictactoe.ui.gameplay.friend.viewmodel.PlayWithFriendsUiState
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlayWithFriendActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlayWithFriendBinding
    private val viewModel: PlayWithFriendViewModel by viewModel()

    companion object {

        fun startActivity(context: Context) {
            context.startActivity(Intent(context, PlayWithFriendActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayWithFriendBinding.inflate(this.layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            viewModel.modalEvent.collect {
                when (it) {
                    is PlayWithFriendsUiState.ModalEvent.UpdatePlayerUiState -> {
                        binding.llPLayerOne.text = String.format(getString(R.string.format_args_turn), it.playerOne.name)
                        binding.ticTacToeBoard.setUpGame(
                            binding.btnPlayAgain,
                            binding.btnHome,
                            binding.llPLayerOne,
                            arrayOf(it.playerOne.name.toString(), it.playerTwo.name.toString())
                        )
                    }
                }
            }
        }
        viewModel.init()
        setupUI()
    }

    private fun setupUI() {
        binding.btnPlayAgain.setOnClickListener {
            binding.ticTacToeBoard?.resetGame()
        }
    }
}