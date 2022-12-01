package com.ajailani.projekan.domain.repository

import com.ajailani.projekan.data.Resource
import com.ajailani.projekan.domain.model.UserProfile
import com.ajailani.projekan.util.ResourceType
import com.ajailani.projekan.util.userProfile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class UserProfileRepositoryFake : UserProfileRepository {
    private lateinit var resourceType: ResourceType

    override fun getUserProfile(): Flow<Resource<UserProfile>> =
        when (resourceType) {
            ResourceType.Success -> flowOf(Resource.Success(userProfile))

            ResourceType.Error -> flowOf(Resource.Error(null))
        }

    fun setResourceType(type: ResourceType) {
        resourceType = type
    }
}