package com.jackson.tictactoe.ui.gameplay.friend.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.jackson.tictactoe.domain.Player
import com.jackson.tictactoe.utils.CloseableCoroutineScope
import com.jackson.tictactoe.utils.SharedPref
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

class PlayWithFriendViewModel(
    coroutineScope: CloseableCoroutineScope,
    private val sharedPref: SharedPref
) : ViewModel(coroutineScope), PlayWithFriendsUiState {

    override val modalEvent: Flow<PlayWithFriendsUiState.ModalEvent>
        get() = _modalEvent.filterNotNull()

    private val _modalEvent: MutableSharedFlow<PlayWithFriendsUiState.ModalEvent?> = MutableSharedFlow()

    private var playerOne = Player()
    private var playerTwo = Player()
    private var isEnableVibration = false
    private var isEnableSound = false

    override fun init() {
        viewModelScope.launch {
            playerOne = sharedPref.getPlayerOneProfile() ?: Player()
            playerTwo = sharedPref.getPlayerTwoProfile() ?: Player()
            isEnableVibration = sharedPref.getBoolean(SharedPref.PREFS_ENABLE_VIBRATION)
            isEnableSound = sharedPref.getBoolean(SharedPref.PREFS_ENABLE_SOUND)
            _modalEvent.emit(PlayWithFriendsUiState.ModalEvent.UpdatePlayerUiState(playerOne, playerTwo))
        }
    }

    fun updateWinSteak(position: Int) {
        if (position == 0) {
            playerOne.winCount = playerOne.winCount +1
            playerOne.totalGamePlay = playerOne.totalGamePlay +1

            playerTwo.lostCount = playerTwo.lostCount +1
            playerTwo.totalGamePlay = playerTwo.totalGamePlay +1

        } else {
            playerTwo.winCount = playerTwo.winCount +1
            playerTwo.totalGamePlay = playerTwo.totalGamePlay +1

            playerOne.lostCount = playerOne.lostCount +1
            playerOne.totalGamePlay = playerOne.totalGamePlay +1
        }

        sharedPref.updatePlayerProfile(convertPlayerToJsonString(playerOne), convertPlayerToJsonString(playerTwo))
    }

    private fun convertPlayerToJsonString(playerName: Player): String {
        return Gson().toJson(playerName)
    }

    fun getVibrationSetting(): Boolean {
        return isEnableVibration
    }

    fun getSoundSetting(): Boolean {
        return isEnableSound
    }
}