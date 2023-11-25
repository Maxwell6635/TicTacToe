package com.jackson.tictactoe.ui.gameplay.nickname

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.jackson.tictactoe.databinding.ActivityGetPlayersNamesBinding
import com.jackson.tictactoe.ui.gameplay.nickname.viewmodel.GetPlayersNamesUiState
import com.jackson.tictactoe.ui.gameplay.nickname.viewmodel.GetPlayersNamesViewModel
import kotlinx.coroutines.launch

class GetPlayersNamesActivity : AppCompatActivity() {

    companion object {
        private const val INTENT_PARAM_IS_AI_MODE = "INTENT_PARAM_IS_AI_MODE"

        fun startActivity(context: Context, isAIMode: Boolean) {
            context.startActivity(Intent(context, GetPlayersNamesActivity::class.java)
                .putExtra(INTENT_PARAM_IS_AI_MODE, isAIMode))
        }
    }

    private lateinit var binding: ActivityGetPlayersNamesBinding
    private val viewModel: GetPlayersNamesViewModel by viewModels()
    private var isAIMode = false

    private var playerOneName = ""
    private var playerTwoName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        isAIMode = intent.getBooleanExtra(INTENT_PARAM_IS_AI_MODE, false)
        binding = ActivityGetPlayersNamesBinding.inflate(this.layoutInflater)
        setContentView(binding.root)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.modalEvent.collect {
                    when(it) {
                        GetPlayersNamesUiState.ModalEvent.HidePlayerTwoUI -> {
                            binding.tilPlayerTwo.visibility = View.GONE
                        }
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

        }
    }
}