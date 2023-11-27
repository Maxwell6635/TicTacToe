package com.jackson.tictactoe.ui.settings.viewmodel

import kotlinx.coroutines.flow.Flow

interface SettingsUiState {
    val modalEvent: Flow<ModalEvent>

    fun init()

    sealed interface ModalEvent {
        data class UpdateSettingsUiState(val isEnableVibration:Boolean, val isEnableSound: Boolean) : ModalEvent
    }
}
