package com.ajailani.projekan.domain.repository

import com.ajailani.projekan.data.Resource
import com.ajailani.projekan.domain.model.UserCredential
import com.ajailani.projekan.util.ResourceType
import com.ajailani.projekan.util.userCredential
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class AuthRepositoryFake : AuthRepository {
    private lateinit var resourceType: ResourceType

    override fun login(username: String, password: String): Flow<Resource<UserCredential>> =
        when (resourceType) {
            ResourceType.Success -> flowOf(Resource.Success(userCredential))

            ResourceType.Error -> flowOf(Resource.Error(null))
        }

    override fun register(
        name: String,
        email: String,
        username: String,
        password: String
    ): Flow<Resource<UserCredential>> =
        when (resourceType) {
            ResourceType.Success -> flowOf(Resource.Success(userCredential))

            ResourceType.Error -> flowOf(Resource.Error(null))
        }

    fun setResourceType(type: ResourceType) {
        resourceType = type
    }
}