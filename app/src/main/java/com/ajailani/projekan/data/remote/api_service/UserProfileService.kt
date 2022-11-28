package com.ajailani.projekan.data.remote.api_service

import com.ajailani.projekan.data.remote.dto.UserProfileDto
import com.ajailani.projekan.data.remote.dto.response.BaseResponse
import retrofit2.Response
import retrofit2.http.GET

interface UserProfileService {
    @GET("profile")
    suspend fun getProfile(): Response<BaseResponse<UserProfileDto>>
}