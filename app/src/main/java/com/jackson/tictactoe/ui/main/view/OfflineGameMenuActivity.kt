package com.jackson.tictactoe.ui.main.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.jackson.tictactoe.databinding.ActivityOfflineGameMenuBinding
import com.jackson.tictactoe.ui.gameplay.nickname.GetPlayersNamesActivity
import com.jackson.tictactoe.ui.main.viewmodel.OfflineGameMenuViewModel
import kotlinx.coroutines.launch

class OfflineGameMenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOfflineGameMenuBinding
    private val viewModel: OfflineGameMenuViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding = ActivityOfflineGameMenuBinding.inflate(this.layoutInflater)
        setContentView(binding.root)


        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.modalEvent.collect {

                }
            }
        }
        initializeUI()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun initializeUI() {
        binding.btnWithFriend.setOnClickListener {
            GetPlayersNamesActivity.startActivity(this@OfflineGameMenuActivity, isAIMode = false)
        }
        binding.btnWithAi.setOnClickListener {
            GetPlayersNamesActivity.startActivity(this@OfflineGameMenuActivity, isAIMode = true)
        }
        binding.ivSettings.setOnClickListener {

        }
    }
}