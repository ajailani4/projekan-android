package com.ajailani.projekan.domain.repository

import com.ajailani.projekan.data.Resource
import com.ajailani.projekan.domain.model.UserCredential
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun login(username: String, password: String): Flow<Resource<UserCredential>>
}