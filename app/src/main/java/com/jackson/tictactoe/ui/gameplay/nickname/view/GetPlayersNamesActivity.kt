package com.jackson.tictactoe.ui.gameplay.nickname.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.jackson.tictactoe.databinding.ActivityGetPlayersNamesBinding
import com.jackson.tictactoe.domain.Player
import com.jackson.tictactoe.ui.gameplay.friend.view.PlayWithFriendActivity
import com.jackson.tictactoe.ui.gameplay.nickname.viewmodel.GetPlayersNamesUiState
import com.jackson.tictactoe.ui.gameplay.nickname.viewmodel.GetPlayersNamesViewModel
import com.jackson.tictactoe.utils.SharedPref
import com.jackson.tictactoe.utils.SharedPref.Companion.PREFS_PLAYER_ONE
import com.jackson.tictactoe.utils.SharedPref.Companion.PREFS_PLAYER_TWO
import kotlinx.coroutines.launch

class GetPlayersNamesActivity : AppCompatActivity() {

    companion object {
        private const val INTENT_PARAM_IS_AI_MODE = "INTENT_PARAM_IS_AI_MODE"

        fun startActivity(context: Context, isAIMode: Boolean) {
            context.startActivity(
                Intent(context, GetPlayersNamesActivity::class.java)
                    .putExtra(INTENT_PARAM_IS_AI_MODE, isAIMode)
            )
        }
    }

    private lateinit var binding: ActivityGetPlayersNamesBinding
    private lateinit var sharedPref: SharedPref
    private val viewModel: GetPlayersNamesViewModel by viewModels()
    private var isAIMode = false
    private var playerOne : Player = Player()
    private var playerTwo: Player = Player()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPref = SharedPref(this@GetPlayersNamesActivity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        isAIMode = intent.getBooleanExtra(INTENT_PARAM_IS_AI_MODE, false)
        binding = ActivityGetPlayersNamesBinding.inflate(this.layoutInflater)
        setContentView(binding.root)
        lifecycleScope.launch {
            viewModel.modalEvent.collect {
                when (it) {
                    GetPlayersNamesUiState.ModalEvent.HidePlayerTwoUI -> {
                        binding.tilPlayerTwo.visibility = View.GONE
                    }
                }
            }
        }
        viewModel.init(isAIMode)
        initializeUI()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun initializeUI() {
        binding.btnNext.setOnClickListener {
            if (viewModel.validateNickName(binding.etPlayerOne.text.toString(), binding.etPlayerTwo.text.toString())) {
                playerOne = Player(name = binding.etPlayerOne.text.toString())
                playerTwo = Player(name = binding.etPlayerTwo.text.toString())

                sharedPref.put(PREFS_PLAYER_ONE, viewModel.convertPlayerToJsonString(playerOne))
                sharedPref.put(PREFS_PLAYER_TWO,  viewModel.convertPlayerToJsonString(playerTwo))

                PlayWithFriendActivity.startActivity(this@GetPlayersNamesActivity)
            }
        }
    }
}