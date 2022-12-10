package com.ajailani.projekan.domain.repository

import com.ajailani.projekan.data.Resource
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun addTask(projectId: String, title: String): Flow<Resource<Any>>
}