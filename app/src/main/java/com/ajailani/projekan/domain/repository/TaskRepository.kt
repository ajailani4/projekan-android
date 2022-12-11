package com.ajailani.projekan.domain.repository

import com.ajailani.projekan.data.Resource
import com.ajailani.projekan.util.TaskStatus
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun addTask(projectId: String, title: String): Flow<Resource<Any>>

    fun editTask(
        id: String,
        title: String,
        status: TaskStatus
    ): Flow<Resource<Any>>
}