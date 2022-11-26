package com.ajailani.projekan.domain.repository

import kotlinx.coroutines.flow.flowOf

class UserCredentialRepositoryFake : UserCredentialRepository {
    private var _accessToken = ""

    override suspend fun saveAccessToken(accessToken: String) {
        _accessToken = accessToken
    }

    override fun getAccessToken() = flowOf(_accessToken)
}