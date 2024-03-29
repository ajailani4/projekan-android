package com.ajailani.projekan.data.remote.data_source

import com.ajailani.projekan.data.remote.api_service.AuthService
import com.ajailani.projekan.data.remote.dto.request.LoginRequest
import com.ajailani.projekan.data.remote.dto.request.RegisterRequest
import javax.inject.Inject

class AuthRemoteDataSource @Inject constructor(
    private val authService: AuthService
) {
    suspend fun login(loginRequest: LoginRequest) =
        authService.login(loginRequest)

    suspend fun register(registerRequest: RegisterRequest) =
        authService.register(registerRequest)
}