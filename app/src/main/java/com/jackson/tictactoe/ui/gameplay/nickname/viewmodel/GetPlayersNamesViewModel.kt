package com.jackson.tictactoe.ui.gameplay.nickname.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jackson.tictactoe.utils.CloseableCoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

class GetPlayersNamesViewModel(
    private val coroutineScope: CloseableCoroutineScope = CloseableCoroutineScope()
) : ViewModel(coroutineScope), GetPlayersNamesUiState {

    override val modalEvent: Flow<GetPlayersNamesUiState.ModalEvent>
        get() = _modalEvent.filterNotNull()

    private val _modalEvent: MutableSharedFlow<GetPlayersNamesUiState.ModalEvent?> = MutableSharedFlow()

    private var isAIMode = false

    override fun init(isAIMode: Boolean) {
        this.isAIMode = isAIMode
        if (isAIMode) {
            viewModelScope.launch {
                _modalEvent.emit(GetPlayersNamesUiState.ModalEvent.HidePlayerTwoUI)
            }
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

}