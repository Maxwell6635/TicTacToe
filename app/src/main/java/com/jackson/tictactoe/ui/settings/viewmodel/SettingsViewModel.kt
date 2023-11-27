package com.jackson.tictactoe.ui.settings.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jackson.tictactoe.utils.CloseableCoroutineScope
import com.jackson.tictactoe.utils.SharedPref
import com.jackson.tictactoe.utils.SharedPref.Companion.PREFS_ENABLE_SOUND
import com.jackson.tictactoe.utils.SharedPref.Companion.PREFS_ENABLE_VIBRATION
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

class SettingsViewModel(
    coroutineScope: CloseableCoroutineScope,
    private val sharedPref: SharedPref
) : ViewModel(coroutineScope), SettingsUiState {

    override val modalEvent: Flow<SettingsUiState.ModalEvent>
        get() = _modalEvent.filterNotNull()

    private val _modalEvent: MutableSharedFlow<SettingsUiState.ModalEvent?> = MutableSharedFlow()

    private var isEnableVibration = false
    private var isEnableSound = false

    override fun init() {
        viewModelScope.launch {
            isEnableVibration = sharedPref.getBoolean(PREFS_ENABLE_VIBRATION)
            isEnableSound = sharedPref.getBoolean(PREFS_ENABLE_SOUND)
            _modalEvent.emit(SettingsUiState.ModalEvent.UpdateSettingsUiState(isEnableVibration, isEnableSound))
        }
    }

    fun updateVibrationEnable(isEnabled: Boolean) {
        sharedPref.put(PREFS_ENABLE_VIBRATION, isEnabled)
    }

    fun updateSoundEnable(isEnabled: Boolean) {
        sharedPref.put(PREFS_ENABLE_SOUND, isEnabled)
    }
    fun resetGame() {
        sharedPref.clear()
    }
}