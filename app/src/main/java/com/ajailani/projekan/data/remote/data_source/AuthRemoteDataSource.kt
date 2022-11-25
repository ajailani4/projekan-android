package com.ajailani.projekan.data.remote.data_source

import com.ajailani.projekan.data.remote.api_service.AuthService
import com.ajailani.projekan.data.remote.dto.request.LoginRequest
import javax.inject.Inject

class AuthRemoteDataSource @Inject constructor(
    private val authService: AuthService
) {
    suspend fun login(loginRequest: LoginRequest) =
        authService.login(loginRequest)
}