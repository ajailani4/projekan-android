package com.ajailani.projekan.domain.repository

import com.ajailani.projekan.data.Resource
import com.ajailani.projekan.domain.model.UserProfile
import kotlinx.coroutines.flow.Flow

interface UserProfileRepository {
    fun getUserProfile(): Flow<Resource<UserProfile>>
}