package com.jackson.tictactoe.ui.main.viewmodel

import androidx.lifecycle.ViewModel
import com.jackson.tictactoe.utils.CloseableCoroutineScope
import com.jackson.tictactoe.utils.SharedPref
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filterNotNull

class OfflineGameMenuViewModel(
    coroutineScope: CloseableCoroutineScope,
    private val sharedPref: SharedPref
) : ViewModel(coroutineScope), OfflineGameMenuUiState {

    override val modalEvent: Flow<OfflineGameMenuUiState.ModalEvent>
        get() = _modalEvent.filterNotNull()

    private val _modalEvent: MutableSharedFlow<OfflineGameMenuUiState.ModalEvent?> = MutableSharedFlow()

    fun checkIsExistingPlayer(): Boolean {
       return sharedPref.getPlayerTwoProfile() != null && sharedPref.getPlayerTwoProfile() != null
    }
}