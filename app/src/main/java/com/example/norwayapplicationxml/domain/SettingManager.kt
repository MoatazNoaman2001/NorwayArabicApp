package com.example.norwayapplicationxml.domain

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking


private val Context.datastore: DataStore<Preferences> by preferencesDataStore(
    name = "settings_preferences"
)

class SettingManager(private val context: Context) {
    companion object {
        private val SELECTED_LANGUAGE = stringPreferencesKey("Language")
        private val SELECTED_THEME_MODE = stringPreferencesKey("Theme_Mode")
    }

    suspend fun saveSelectedLanguage(language: String) {
        context.applicationContext.datastore.edit {
            it[SELECTED_LANGUAGE] = language
            LanguageHelper.changeLanguage(context, language)
        }
    }

    suspend fun saveThemeMode(v: Int) {
        context.applicationContext.datastore.edit {
            it[SELECTED_THEME_MODE] = v.toString()
        }
    }

    private val themeFlow: Flow<String?> = context.applicationContext.datastore.data.map {
        it[SELECTED_THEME_MODE]
    }
    fun observeThemeFlow() = themeFlow

    private val languageFlow: Flow<String?> = context.applicationContext.datastore.data.map {
        it[SELECTED_LANGUAGE]
    }
    fun observeLanguageFlow() = languageFlow


}