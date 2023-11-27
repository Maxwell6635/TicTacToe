package com.jackson.tictactoe.ui.gameplay.friend.viewmodel

import com.jackson.tictactoe.domain.Player
import kotlinx.coroutines.flow.Flow

interface PlayWithFriendsUiState {
    val modalEvent: Flow<ModalEvent>

    fun init()

    sealed interface ModalEvent {
        data class UpdatePlayerUiState(val playerOne:Player, val playerTwo: Player) : ModalEvent
    }
}
