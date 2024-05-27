package com.ajailani.projekan.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.ajailani.projekan.util.Constants
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PreferencesDataStore @Inject constructor(
    @ApplicationContext private val context: Context
) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
            Constants.DataStore.PREFERENCES_NAME
        )
        private val ACCESS_TOKEN = stringPreferencesKey(Constants.DataStore.ACCESS_TOKEN_KEY)
        private val CONVERSION_DATA= stringPreferencesKey(Constants.DataStore.CONVERSION_DATA_KEY)
    }
    suspend fun saveConversionData(conversionData: String) {
        context.dataStore.edit {
            it[CONVERSION_DATA] = conversionData
        }
    }
    fun getConversionData() =
        context.dataStore.data.map { it[CONVERSION_DATA] ?: "" }
    suspend fun saveAccessToken(accessToken: String) {
        context.dataStore.edit {
            it[ACCESS_TOKEN] = accessToken
        }
    }

    fun getAccessToken() =
        context.dataStore.data.map { it[ACCESS_TOKEN] ?: "" }
}