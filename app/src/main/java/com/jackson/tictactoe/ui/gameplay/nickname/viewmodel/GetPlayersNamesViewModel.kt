package com.jackson.tictactoe.ui.gameplay.nickname.viewmodel

import androidx.lifecycle.ViewModel
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

    override fun init(isAIMode: Boolean) {
        if (!isAIMode) {
            coroutineScope.launch {
                _modalEvent.emit(GetPlayersNamesUiState.ModalEvent.HidePlayerTwoUI)
            }
        }
    }

    private val _modalEvent: MutableSharedFlow<GetPlayersNamesUiState.ModalEvent?> = MutableSharedFlow()

}