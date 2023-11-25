package com.jackson.tictactoe.ui.main.viewmodel

import androidx.lifecycle.ViewModel
import com.jackson.tictactoe.utils.CloseableCoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filterNotNull

class OfflineGameMenuViewModel(
    private val coroutineScope: CloseableCoroutineScope = CloseableCoroutineScope()
) : ViewModel(coroutineScope), OfflineGameMenuUiState {

    override val modalEvent: Flow<OfflineGameMenuUiState.ModalEvent>
        get() = _modalEvent.filterNotNull()

    private val _modalEvent: MutableSharedFlow<OfflineGameMenuUiState.ModalEvent?> = MutableSharedFlow()

}