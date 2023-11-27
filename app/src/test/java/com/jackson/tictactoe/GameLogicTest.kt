package com.jackson.tictactoe

import com.jackson.tictactoe.utils.GameLogic
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertNotEquals

import org.junit.Before
import org.junit.Test


class GameLogicTest {
    private var gameLogic: GameLogic? = null
    
    @Before
    fun setUp() {
        gameLogic = GameLogic()
    }

    @Test
    fun testUpdateGameBoard_PositiveCase() {
        gameLogic?.updateGameBoard(1, 1)?.let { assertTrue(it) }
        assertEquals(1, gameLogic?.gameBoard?.get(0)?.get(0) ?: null)
        assertEquals("Player 2's Turn", gameLogic?.playerTurn?.text)
    }

    @Test
    fun testUpdateGameBoard_NegativeCase() {
        gameLogic?.updateGameBoard(1, 1)
        gameLogic?.updateGameBoard(1, 1)?.let { assertFalse(it) }
        assertEquals(1, gameLogic?.gameBoard?.get(0)?.get(0) ?: 0)
        assertEquals("Player 2's Turn", gameLogic?.playerTurn?.text)
    }

    @Test
    fun testWinnerCheck_PositiveCase() {
        gameLogic?.gameBoard = arrayOf(intArrayOf(1, 1, 1), intArrayOf(0, 0, 0), intArrayOf(0, 0, 0))
        gameLogic?.winnerCheck()?.let { assertTrue(it) }
        assertArrayEquals(intArrayOf(0, 0, 1), gameLogic?.winType)
        assertEquals("Player 1 has Won??", gameLogic?.playerTurn?.text)
    }

    @Test
    fun testWinnerCheck_NegativeCase() {
        gameLogic?.gameBoard = arrayOf(intArrayOf(1, 2, 1), intArrayOf(0, 0, 0), intArrayOf(0, 0, 0))
        gameLogic?.winnerCheck()?.let { assertFalse(it) }
        assertArrayEquals(intArrayOf(-1, -1, -1), gameLogic?.winType)
        assertNotEquals("Player 1 has Won??", gameLogic?.playerTurn?.text)
    }

    @Test
    fun testResetGame() {
        gameLogic?.gameBoard = arrayOf(intArrayOf(1, 2, 1), intArrayOf(2, 1, 2), intArrayOf(1, 2, 1))
        gameLogic?.resetGame()
        assertArrayEquals(arrayOf(intArrayOf(0, 0, 0), intArrayOf(0, 0, 0), intArrayOf(0, 0, 0)), gameLogic?.gameBoard)
        assertEquals(1, gameLogic?.player)
        assertEquals("Player 2's Turn", gameLogic?.playerTurn?.text)
    }

    @Test
    fun testSetPlayerNames() {
        val playerNames = arrayOf("John", "Jane")
        gameLogic?.setUpdatedPlayerNames(playerNames)
        assertArrayEquals(playerNames, gameLogic?.playerNames)
    }
}