package com.jackson.tictactoe.domain

data class Player(
    var name: String? = "",
    var winCount: Int = 0,
    var lostCount: Int = 0,
    var totalGamePlay: Int = 0
) {

    val winningRate : Int
        get() = if (totalGamePlay != 0) {
            winCount / totalGamePlay
        } else {
            0
        }
}