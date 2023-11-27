package com.jackson.tictactoe.domain

data class Player(
    val name: String? = "",
    val winCount: Int = 0,
    val lostCount: Int = 0,
    val totalGamePlay: Int = 0
)