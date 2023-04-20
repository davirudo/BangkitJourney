package com.example.katahati.utils

import android.content.Context
import android.content.SharedPreferences

class SessionManager(_context: Context) {
    private var pref: SharedPreferences
    private var editor: SharedPreferences.Editor
    private var PRIVATE_MODE = 0

    init {
        pref = _context.getSharedPreferences(PREF, PRIVATE_MODE)
        editor = pref.edit()
    }

    fun saveString(key: String, value: String) {
        editor.putString(key, value)
        editor.commit()
    }

    fun getString(key: String): String? {
        return pref.getString(key, "")
    }

    fun saveBoolean(key: String, value: Boolean) {
        editor.putBoolean(key, value)
        editor.commit()
    }

    fun getBoolean(key: String): Boolean {
        return pref.getBoolean(key, false)
    }

    fun clearSession() {
        editor.clear()
        editor.commit()
    }

    companion object {
        private const val PREF = "pref"
    }
}