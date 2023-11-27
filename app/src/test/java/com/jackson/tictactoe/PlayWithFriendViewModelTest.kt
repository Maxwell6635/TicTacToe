package com.jackson.tictactoe

import com.google.gson.Gson
import com.jackson.tictactoe.domain.Player
import com.jackson.tictactoe.ui.gameplay.friend.viewmodel.PlayWithFriendViewModel
import com.jackson.tictactoe.ui.gameplay.friend.viewmodel.PlayWithFriendsUiState
import com.jackson.tictactoe.utils.CloseableCoroutineScope
import com.jackson.tictactoe.utils.SharedPref
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class PlayWithFriendViewModelTest {
    private lateinit var viewModel: PlayWithFriendViewModel

    @MockK
    private lateinit var sharedPref: SharedPref

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = PlayWithFriendViewModel(CloseableCoroutineScope(), sharedPref)
    }

    @Test
    fun `init should update player UI state`() = runBlockingTest {
        // Arrange
        val playerOne = Player("John", 5, 3, 8)
        val playerTwo = Player("Jane", 4, 4, 8)
        coEvery { sharedPref.getPlayerOneProfile() } returns playerOne
        coEvery { sharedPref.getPlayerTwoProfile() } returns playerTwo
        coEvery { sharedPref.getBoolean(SharedPref.PREFS_ENABLE_VIBRATION) } returns true
        coEvery { sharedPref.getBoolean(SharedPref.PREFS_ENABLE_SOUND) } returns false

        // Act
        viewModel.init()

        // Assert
        viewModel.modalEvent.collect { event ->
            if (event is PlayWithFriendsUiState.ModalEvent.UpdatePlayerUiState) {
                assertEquals(playerOne, event.playerOne)
                assertEquals(playerTwo, event.playerTwo)
            }
        }
    }

    @Test
    fun `updateWinSteak should update player profiles`() {
        // Arrange
        val playerOne = Player("John", 5, 3, 8)
        val playerTwo = Player("Jane", 4, 4, 8)
        coEvery { sharedPref.updatePlayerProfile(any(), any()) } returns Unit

        // Act
        viewModel.updateWinSteak(0)

        // Assert
        coEvery { sharedPref.updatePlayerProfile(any(), any()) } answers {
            val playerOneJson = firstArg<String>()
            val playerTwoJson = secondArg<String>()
            val updatedPlayerOne = Gson().fromJson(playerOneJson, Player::class.java)
            val updatedPlayerTwo = Gson().fromJson(playerTwoJson, Player::class.java)
            assertEquals(playerOne.winCount + 1, updatedPlayerOne.winCount)
            assertEquals(playerOne.totalGamePlay + 1, updatedPlayerOne.totalGamePlay)
            assertEquals(playerTwo.lostCount + 1, updatedPlayerTwo.lostCount)
            assertEquals(playerTwo.totalGamePlay + 1, updatedPlayerTwo.totalGamePlay)
        }
    }

    @Test
    fun `getVibrationSetting should return correct value`() {
        // Arrange
        coEvery { sharedPref.getBoolean(SharedPref.PREFS_ENABLE_VIBRATION) } returns true

        // Act
        val result = viewModel.getVibrationSetting()

        // Assert
        assertEquals(true, result)
    }

    @Test
    fun `getSoundSetting should return correct value`() {
        // Arrange
        coEvery { sharedPref.getBoolean(SharedPref.PREFS_ENABLE_SOUND) } returns false

        // Act
        val result = viewModel.getSoundSetting()

        // Assert
        assertEquals(false, result)
    }
}