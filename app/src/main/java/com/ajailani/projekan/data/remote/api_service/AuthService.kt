package com.ajailani.projekan.data.remote.api_service

import com.ajailani.projekan.data.remote.dto.UserCredentialDto
import com.ajailani.projekan.data.remote.dto.request.LoginRequest
import com.ajailani.projekan.data.remote.dto.response.BaseResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): Response<BaseResponse<UserCredentialDto>>
}