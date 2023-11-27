package com.jackson.tictactoe.ui.gameplay.friend

interface UpdatePlayerGame {
    fun updatePlayerWinStreak(position: Int, isTieGame: Boolean)

    fun makeSoundAndVibration(position: Int)
}