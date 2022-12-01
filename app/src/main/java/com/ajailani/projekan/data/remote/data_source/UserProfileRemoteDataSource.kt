package com.ajailani.projekan.data.remote.data_source

import com.ajailani.projekan.data.remote.api_service.UserProfileService
import javax.inject.Inject

class UserProfileRemoteDataSource @Inject constructor(
    private val userProfileService: UserProfileService
) {
    suspend fun getProfile() = userProfileService.getProfile()
}