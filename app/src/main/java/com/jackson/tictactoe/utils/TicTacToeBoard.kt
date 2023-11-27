package com.jackson.tictactoe.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import com.jackson.tictactoe.R
import com.jackson.tictactoe.domain.Player
import com.jackson.tictactoe.ui.gameplay.friend.UpdatePlayerGame
import kotlin.math.ceil

class TicTacToeBoard(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    private var boardColor = 0
    private var XColor = 0
    private var OColor = 0
    private var winningLineColor = 0
    private var winningLine = false
    private val paint = Paint()
    private var cellSize = width / 3
    private val game: GameLogic = GameLogic()
    private var listener : UpdatePlayerGame? = null

    init {
        val a = context.theme.obtainStyledAttributes(attrs, R.styleable.TicTacToeBoard, 0, 0)
        try {
            boardColor = a.getInteger(R.styleable.TicTacToeBoard_boardColor, 0)
            XColor = a.getInteger(R.styleable.TicTacToeBoard_XColor, 0)
            OColor = a.getInteger(R.styleable.TicTacToeBoard_OColor, 0)
            winningLineColor = a.getInteger(R.styleable.TicTacToeBoard_winningLineColor, 0)
        } finally {
            a.recycle()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val dimension = Math.min(measuredWidth, measuredHeight)
        cellSize = dimension / 3
        setMeasuredDimension(dimension, dimension)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        paint.style = Paint.Style.STROKE
        paint.isAntiAlias = true
        drawGameBoard(canvas)
        drawMarkers(canvas)
        if (winningLine) {
            paint.color = winningLineColor
            drawWinningLine(canvas)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y
        val action = event.action
        if (action == MotionEvent.ACTION_DOWN) {
            val row = ceil((y / cellSize).toDouble()).toInt()
            val col = ceil((x / cellSize).toDouble()).toInt()
            if (!winningLine) {
                if (game.updateGameBoard(row, col)) {
                    invalidate()
                    if (game.winnerCheck()) {
                        listener?.updatePlayerWinStreak(game.player, game.winType[2] == 5)
                        invalidate()
                    }
                    if (game.player % 2 == 0) {
                        game.player = game.player - 1
                        listener?.makeSoundAndVibration(game.player)
                    } else {
                        game.player = game.player + 1
                        listener?.makeSoundAndVibration(game.player)
                    }
                }
            }
            invalidate()
            return true
        }
        return false
    }

    private fun drawGameBoard(canvas: Canvas) {
        paint.color = boardColor
        paint.strokeWidth = 16f
        for (c in 1..2) {
            canvas.drawLine((cellSize * c).toFloat(), 0f, (cellSize * c).toFloat(), canvas.width.toFloat(), paint)
        }
        for (r in 1..2) {
            canvas.drawLine(0f, (cellSize * r).toFloat(), canvas.width.toFloat(), (cellSize * r).toFloat(), paint)
        }
    }

    private fun drawX(canvas: Canvas, row: Int, col: Int) {
        paint.color = XColor
        canvas.drawLine(
            ((col + 1) * cellSize - cellSize * 0.2).toFloat(),
            (row * cellSize + cellSize * 0.2).toFloat(),
            (col * cellSize + cellSize * 0.2).toFloat(),
            ((row + 1) * cellSize - cellSize * 0.2).toFloat(),
            paint
        )
        canvas.drawLine(
            (col * cellSize + cellSize * 0.2).toFloat(),
            (row * cellSize + cellSize * 0.2).toFloat(),
            ((col + 1) * cellSize - cellSize * 0.2).toFloat(),
            ((row + 1) * cellSize - cellSize * 0.2).toFloat(),
            paint
        )
    }

    private fun drawOval(canvas: Canvas, row: Int, col: Int) {
        paint.color = OColor
        canvas.drawOval(
            (col * cellSize + cellSize * 0.2).toFloat(),
            (row * cellSize + cellSize * 0.2).toFloat(),
            (col * cellSize + cellSize - cellSize * 0.2).toFloat(),
            (row * cellSize + cellSize - cellSize * 0.2).toFloat(),
            paint
        )
    }

    private fun drawMarkers(canvas: Canvas) {
        for (r in 0..2) {
            for (c in 0..2) {
                if (game.gameBoard[r][c] != 0) {
                    if (game.gameBoard[r][c] == 1) {
                        drawX(canvas, r, c)
                    } else {
                        drawOval(canvas, r, c)
                    }
                }
            }
        }
    }

    private fun drawHorizontalLine(canvas: Canvas, row: Int, col: Int) {
        canvas.drawLine(
            col.toFloat(),
            (row * cellSize + cellSize / 2).toFloat(),
            (cellSize * 3).toFloat(),
            (row * cellSize + cellSize / 2).toFloat(),
            paint
        )
    }

    private fun drawVerticalLine(canvas: Canvas, row: Int, col: Int) {
        canvas.drawLine(
            (col * cellSize + cellSize / 2).toFloat(),
            row.toFloat(),
            (col * cellSize + cellSize / 2).toFloat(),
            (cellSize * 3).toFloat(),
            paint
        )
    }

    private fun drawDiagonalLinePos(canvas: Canvas) {
        canvas.drawLine(0f, (cellSize * 3).toFloat(), (cellSize * 3).toFloat(), 0f, paint)
    }

    private fun drawDiagonalLineNeg(canvas: Canvas) {
        canvas.drawLine(0f, 0f, (cellSize * 3).toFloat(), (cellSize * 3).toFloat(), paint)
    }

    private fun drawWinningLine(canvas: Canvas) {
        val row = game.winType[0]
        val col = game.winType[1]
        when (game.winType[2]) {
            1 -> drawHorizontalLine(canvas, row, col)
            2 -> drawVerticalLine(canvas, row, col)
            3 -> drawDiagonalLineNeg(canvas)
            4 -> drawDiagonalLinePos(canvas)
        }
    }

    fun setUpGame(playerDisplay: TextView?, names: Array<Player>, updatePlayerGame: UpdatePlayerGame) {
        game.playerTurn = playerDisplay
        game.setPlayerNames(arrayOf(names[0].name.toString(), names[1].name.toString()))
        listener = updatePlayerGame
    }

    fun resetGame() {
        game.resetGame()
        winningLine = false
        invalidate()
    }
}