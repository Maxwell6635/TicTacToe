package com.jackson.tictactoe.ui.gameplay.nickname.viewmodel

import kotlinx.coroutines.flow.Flow

interface GetPlayersNamesUiState {
    val modalEvent: Flow<ModalEvent>

    fun init(isAIMode: Boolean)

    sealed interface ModalEvent {
        object HidePlayerTwoUI : ModalEvent
    }
}
