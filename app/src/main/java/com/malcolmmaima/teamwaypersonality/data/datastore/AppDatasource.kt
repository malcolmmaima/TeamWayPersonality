package com.malcolmmaima.teamwaypersonality.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "teamway_data")

class AppDatasource @Inject constructor(@ApplicationContext context: Context) {
    private val applicationContext = context.applicationContext

    companion object {
        val CURRENT_PERSONALITY = stringPreferencesKey("current_personality")
    }

    suspend fun saveCurrentPersonality(personality: String) {
        applicationContext.dataStore.edit { preferences ->
            preferences[CURRENT_PERSONALITY] = personality
        }
    }

    suspend fun clear() {
        applicationContext.dataStore.edit {
            it.clear()
        }
    }
}