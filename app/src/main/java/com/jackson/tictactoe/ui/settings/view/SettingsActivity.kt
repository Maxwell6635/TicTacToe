package com.jackson.tictactoe.ui.settings.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.jackson.tictactoe.databinding.ActivitySettingsBinding
import com.jackson.tictactoe.ui.settings.viewmodel.SettingsUiState
import com.jackson.tictactoe.ui.settings.viewmodel.SettingsViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private val viewModel: SettingsViewModel by viewModel()

    companion object {
        fun startActivity(context: Context) {
            context.startActivity(
                Intent(context, SettingsActivity::class.java)
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding = ActivitySettingsBinding.inflate(this.layoutInflater)
        setContentView(binding.root)
        lifecycleScope.launch {
            viewModel.modalEvent.collect {
                when (it) {
                    is SettingsUiState.ModalEvent.UpdateSettingsUiState -> {
                        binding.swSound.isChecked = it.isEnableSound
                        binding.swSound.isChecked = it.isEnableVibration
                    }
                }
            }
        }
        initializeUI()
        viewModel.init()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun initializeUI() {
        binding.swSound.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                viewModel.updateSoundEnable(true)
            } else {
                viewModel.updateSoundEnable(false)
            }
        }
        binding.swVibration.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                viewModel.updateVibrationEnable(true)
            } else {
                viewModel.updateVibrationEnable(false)
            }
        }
        binding.btnReset.setOnClickListener {
            viewModel.resetGame()
        }
    }

}