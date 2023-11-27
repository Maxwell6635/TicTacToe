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
    override fun init() {
        viewModelScope.launch {
            playerOne = sharedPref.getPlayerOneProfile() ?: Player()
            playerTwo = sharedPref.getPlayerTwoProfile() ?: Player()
            _modalEvent.emit(PlayWithFriendsUiState.ModalEvent.UpdatePlayerUiState(playerOne, playerTwo))
        }
    }

    fun validateNickName(playerOneName: String?, playerTwoName: String?): Boolean {
        var isValid = true
        if (playerOneName.isNullOrEmpty()) {
            isValid = false
        } else if (playerTwoName.isNullOrEmpty()) {
            isValid = false
        }
        return isValid
    }

    fun convertPlayerToJsonString(playerName: Player): String {
        return Gson().toJson(playerName)
    }

}