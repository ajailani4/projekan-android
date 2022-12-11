package com.ajailani.projekan.domain.repository

import com.ajailani.projekan.data.Resource
import com.ajailani.projekan.util.ResourceType
import com.ajailani.projekan.util.TaskStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class TaskRepositoryFake : TaskRepository {
    private lateinit var resourceType: ResourceType

    override fun addTask(projectId: String, title: String): Flow<Resource<Any>> =
        when (resourceType) {
            ResourceType.Success -> flowOf(Resource.Success(Any()))

            ResourceType.Error -> flowOf(Resource.Error(null))
        }

    override fun editTask(id: String, title: String, status: TaskStatus?): Flow<Resource<Any>> {
        TODO("Not yet implemented")
    }

    fun setResourceType(type: ResourceType) {
        resourceType = type
    }
}