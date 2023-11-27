package com.jackson.tictactoe.ui.main.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.jackson.tictactoe.databinding.ActivityOfflineGameMenuBinding
import com.jackson.tictactoe.ui.gameplay.friend.view.PlayWithFriendActivity
import com.jackson.tictactoe.ui.gameplay.nickname.view.GetPlayersNamesActivity
import com.jackson.tictactoe.ui.main.viewmodel.OfflineGameMenuViewModel
import com.jackson.tictactoe.ui.settings.SettingsActivity
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class OfflineGameMenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOfflineGameMenuBinding
    private val viewModel: OfflineGameMenuViewModel by viewModel()

    companion object {
        fun startActivity(context: Context) {
            context.startActivity(Intent(context, OfflineGameMenuActivity::class.java))
        }
    }

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
            if (viewModel.checkIsExistingPlayer()) {
                PlayWithFriendActivity.startActivity(this@OfflineGameMenuActivity)
            } else {
                GetPlayersNamesActivity.startActivity(this@OfflineGameMenuActivity, isAIMode = false)
            }
        }
        binding.ivSettings.setOnClickListener {
            SettingsActivity.startActivity(this@OfflineGameMenuActivity)
        }
    }
}