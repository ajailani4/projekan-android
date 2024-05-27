package com.ajailani.projekan.domain.repository

import kotlinx.coroutines.flow.flowOf

class UserCredentialRepositoryFake : UserCredentialRepository {
    private var _accessToken = ""
    private var _conversionData = ""

    override suspend fun saveAccessToken(accessToken: String) {
        _accessToken = accessToken
    }

    override fun getAccessToken() = flowOf(_accessToken)
    override suspend fun saveConversionData(conversionData: String) {
        _conversionData = conversionData
    }

    override fun getConversionData()= flowOf(_conversionData)


}