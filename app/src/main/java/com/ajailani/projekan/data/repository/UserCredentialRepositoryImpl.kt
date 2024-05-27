package com.ajailani.projekan.data.repository

import com.ajailani.projekan.data.local.PreferencesDataStore
import com.ajailani.projekan.domain.repository.UserCredentialRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserCredentialRepositoryImpl @Inject constructor(
    private val preferencesDataStore: PreferencesDataStore
) : UserCredentialRepository {
    override suspend fun saveAccessToken(accessToken: String) {
        preferencesDataStore.saveAccessToken(accessToken)
    }

    override fun getAccessToken() = preferencesDataStore.getAccessToken()
    override suspend fun saveConversionData(conversionData: String) {
        preferencesDataStore.saveConversionData(conversionData)
    }

    override fun getConversionData()=preferencesDataStore.getConversionData()
}