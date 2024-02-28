package com.example.tictactoe

import android.content.Context
import android.content.res.Configuration

object isDarkThemeCheck {
    fun isDarkMode(context: Context): Boolean {
        return when (context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> true
            else -> false
        }
    }
}