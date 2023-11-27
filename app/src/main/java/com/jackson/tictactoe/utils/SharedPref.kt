package com.jackson.tictactoe.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.jackson.tictactoe.domain.Player

class SharedPref(context: Context) {

    companion object {
        private const val PREFS_NAME = "sharedpref_tictactoe"
        const val PREFS_PLAYER_ONE_NAME = "PREFS_PLAYER_ONE_NAME"
        const val PREFS_PLAYER_TWO_NAME = "PREFS_PLAYER_TWO_NAME"
        const val PREFS_PLAYER_ONE = "PREFS_PLAYER_ONE"
        const val PREFS_PLAYER_TWO = "PREFS_PLAYER_ONE"
    }

    private val sharedPref: SharedPreferences
    private val editor: SharedPreferences.Editor

    init {
        sharedPref = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        editor = sharedPref.edit()
    }

    fun put(key: String, value: String) {
        editor.putString(key, value)
            .apply()
    }

    fun put(key: String, value: Boolean) {
        editor.putBoolean(key, value)
            .apply()
    }

    fun getBoolean(key: String): Boolean {
        return sharedPref.getBoolean(key, false)
    }

    fun getString(key: String): String? {
        return sharedPref.getString(key, null)
    }

    fun getPlayerOneProfile(): Player? {
        val jsonInString = sharedPref.getString(PREFS_PLAYER_ONE, "")
        return try {
            Gson().fromJson(jsonInString, Player::class.java)
        } catch (e: Exception) {
            Player()
        }
        return Player()
    }

    fun getPlayerTwoProfile(): Player? {
        val jsonInString = sharedPref.getString(PREFS_PLAYER_TWO, "")
        return try {
            Gson().fromJson(jsonInString, Player::class.java)
        } catch (e: Exception) {
            Player()
        }
        return Player()
    }

    fun clear() {
        editor.clear()
            .apply()
    }

}