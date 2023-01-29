package com.nima.guessthatpokemon

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ThemeDataStore(private val context: Context) {
    companion object{
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("theme")

        val themeKey = booleanPreferencesKey("app_theme")
    }

    val getTheme: Flow<Boolean?> = context.dataStore.data
            .map{ pref ->
                pref[themeKey] ?: true
            }

    suspend fun saveTheme(isDark: Boolean){
        context.dataStore.edit {
            it[themeKey] = isDark
        }
    }
}