package com.jackson.tictactoe.ui.gameplay.friend.view

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.jackson.tictactoe.R
import com.jackson.tictactoe.databinding.ActivityPlayWithFriendBinding
import com.jackson.tictactoe.databinding.CelebrateDialogBinding
import com.jackson.tictactoe.databinding.DrawDialogBinding
import com.jackson.tictactoe.ui.gameplay.friend.UpdatePlayerGame
import com.jackson.tictactoe.ui.gameplay.friend.viewmodel.PlayWithFriendViewModel
import com.jackson.tictactoe.ui.gameplay.friend.viewmodel.PlayWithFriendsUiState
import com.jackson.tictactoe.ui.main.view.OfflineGameMenuActivity
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlayWithFriendActivity : AppCompatActivity(), UpdatePlayerGame {

    private lateinit var binding: ActivityPlayWithFriendBinding
    private val viewModel: PlayWithFriendViewModel by viewModel()
    private var dialog: Dialog? = null
    private var vibrator: Vibrator? = null

    companion object {
        fun startActivity(context: Context) {
            context.startActivity(Intent(context, PlayWithFriendActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayWithFriendBinding.inflate(this.layoutInflater)
        setContentView(binding.root)
        dialog = Dialog(this)
        vibrator = getSystemService(VIBRATOR_SERVICE) as Vibrator
        lifecycleScope.launch {
            viewModel.modalEvent.collect {
                when (it) {
                    is PlayWithFriendsUiState.ModalEvent.UpdatePlayerUiState -> {
                        binding.llPLayerOne.text = String.format(getString(R.string.format_args_turn), it.playerOne.name)
                        binding.playerName.text = it.playerOne.name
                        binding.winningRate.text = String.format(getString(R.string.format_args_winning_rate), it.playerOne.winningRate)
                        binding.ticTacToeBoard.setUpGame(
                            binding.llPLayerOne,
                            arrayOf(it.playerOne, it.playerTwo),
                            this@PlayWithFriendActivity
                        )
                    }
                }
            }
        }
        viewModel.init()
    }

    override fun updatePlayerWinStreak(position: Int, isTieGame: Boolean) {
        if (isTieGame) {
            showDrawDialog()
        } else {
            if (position - 1 == 0) {
                celebrateDialog(0)
                viewModel.updateWinSteak(0)
            } else {
                celebrateDialog(1)
                viewModel.updateWinSteak(1)
            }
        }
    }

    override fun makeSoundAndVibration(position: Int) {
//            if (MyServices.SOUND_CHECK) {
        val mp = MediaPlayer.create(this, if (position - 1 == 0) R.raw.x else R.raw.o)
        mp.start()
//            }

//        if (MyServices.VIBRATION_CHECK) {
        if (Build.VERSION.SDK_INT >= 26) {
            vibrator?.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            vibrator?.vibrate(200)
        }
//        }
    }

    private fun celebrateDialog(player_check: Int) {
        val binding = CelebrateDialogBinding.inflate(this.layoutInflater)
        dialog?.setContentView(binding.root)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.setCanceledOnTouchOutside(false)
        val handler = Handler()
        handler.postDelayed({
            binding.celebrateAnimationView.visibility = View.GONE
            binding.container.visibility = View.VISIBLE
            if (player_check == 0) {
                binding.playerImg.setImageResource(R.drawable.cross)
            } else if (player_check == 1) {
                binding.playerImg.setImageResource(R.drawable.circle)
            }
        }, 2300)
        binding.quitBtn.setOnClickListener {
            dialog?.dismiss()
            OfflineGameMenuActivity.startActivity(this@PlayWithFriendActivity)
        }
        binding.continueBtn.setOnClickListener {
            dialog?.dismiss()
            viewModel.init()
            this@PlayWithFriendActivity.binding.ticTacToeBoard.resetGame()
        }
        dialog?.setCanceledOnTouchOutside(false)
        dialog?.setCancelable(false)
        dialog?.show()
    }

    private fun showDrawDialog() {
        val binding = DrawDialogBinding.inflate(this.layoutInflater)
        dialog?.setContentView(binding.root)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.setCanceledOnTouchOutside(false)
        binding.quitBtn.setOnClickListener {
            dialog?.dismiss()
            OfflineGameMenuActivity.startActivity(this@PlayWithFriendActivity)
        }
        binding.continueBtn.setOnClickListener {
            dialog?.dismiss()
            viewModel.init()
            this@PlayWithFriendActivity.binding.ticTacToeBoard.resetGame()
        }
        dialog?.setCanceledOnTouchOutside(false)
        dialog?.setCancelable(false)
        dialog?.show()
    }
}