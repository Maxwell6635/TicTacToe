package com.jackson.tictactoe.ui.main.viewmodel

import kotlinx.coroutines.flow.Flow

interface OfflineGameMenuUiState {
    val modalEvent: Flow<ModalEvent>
    sealed interface ModalEvent {
    }
}
