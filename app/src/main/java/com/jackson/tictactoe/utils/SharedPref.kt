package com.jackson.tictactoe.utils

import android.content.Context
import android.content.SharedPreferences

class SharedPref(context: Context) {

    companion object {
        private const val PREFS_NAME = "sharedpref_tictactoe"
        const val PREFS_PLAYER_ONE_NAME = "PREFS_PLAYER_ONE_NAME"
        const val PREFS_PLAYER_TWO_NAME = "PREFS_PLAYER_TWO_NAME"
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

    fun clear() {
        editor.clear()
            .apply()
    }

}