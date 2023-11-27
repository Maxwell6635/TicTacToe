package com.jackson.tictactoe.utils

import android.view.View
import android.widget.Button
import android.widget.TextView

class GameLogic internal constructor() {
    val gameBoard: Array<IntArray> = Array(3) { IntArray(3) }
    private var playerNames = arrayOf("Player 1", "Player 2")
    var playAgainBtn: Button? = null
    var homeBtn: Button? = null
    var playerTurn: TextView? = null
    var player = 1
    var winType = intArrayOf(-1, -1, -1)
        private set

    init {
        for (r in 0..2) {
            for (c in 0..2) {
                gameBoard[r][c] = 0
            }
        }
    }

    fun updateGameBoard(row: Int, col: Int): Boolean {
        return if (gameBoard[row - 1][col - 1] == 0) {
            gameBoard[row - 1][col - 1] = player
            if (player == 1) {
                playerTurn!!.text = playerNames[1] + "'s Turn"
            } else {
                playerTurn!!.text = playerNames[0] + "'s Turn"
            }
            true
        } else {
            false
        }
    }

    fun winnerCheck(): Boolean {
        var isWinner = false
        for (r in 0..2) {
            if (gameBoard[r][0] == gameBoard[r][1] && gameBoard[r][0] == gameBoard[r][2] && gameBoard[r][0] != 0) {
                winType = intArrayOf(r, 0, 1)
                isWinner = true
            }
        }
        for (c in 0..2) {
            if (gameBoard[c][0] == gameBoard[c][1] && gameBoard[c][0] == gameBoard[c][2] && gameBoard[c][0] != 0) {
                winType = intArrayOf(0, c, 2)
                isWinner = true
            }
        }
        if (gameBoard[0][0] == gameBoard[1][1] && gameBoard[0][0] == gameBoard[2][2] && gameBoard[0][0] != 0) {
            winType = intArrayOf(0, 2, 3)
            isWinner = true
        }
        if (gameBoard[2][0] == gameBoard[1][1] && gameBoard[2][0] == gameBoard[0][2] && gameBoard[2][0] != 0) {
            winType = intArrayOf(2, 2, 4)
            isWinner = true
        }
        var boardFilled = 0
        for (r in 0..2) {
            for (c in 0..2) {
                if (gameBoard[r][c] != 0) {
                    boardFilled += 1
                }
            }
        }
        return if (isWinner) {
            playAgainBtn!!.visibility = View.VISIBLE
            homeBtn!!.visibility = View.VISIBLE
            playerTurn!!.text = playerNames[player - 1] + "has Won!!!!"
            true
        } else if (boardFilled == 9) {
            playAgainBtn!!.visibility = View.VISIBLE
            homeBtn!!.visibility = View.VISIBLE
            playerTurn!!.text = "Tie Game!!!!!"
            true
        } else {
            false
        }
    }

    fun resetGame() {
        for (r in 0..2) {
            for (c in 0..2) {
                gameBoard[r][c] = 0
            }
        }
        player = 1
        playAgainBtn!!.visibility = View.GONE
        homeBtn!!.visibility = View.GONE
        playerTurn!!.text = playerNames[1] + "'s Turn"
    }

    fun setPlayerNames(playerNames: Array<String>) {
        this.playerNames = playerNames
    }
}