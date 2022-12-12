package com.ajailani.projekan.data.remote.data_source

import com.ajailani.projekan.data.remote.api_service.TaskService
import com.ajailani.projekan.data.remote.dto.request.TaskRequest
import javax.inject.Inject

class TaskRemoteDataSource @Inject constructor(
    private val taskService: TaskService
) {
    suspend fun addTask(taskRequest: TaskRequest) = taskService.addTask(taskRequest)

    suspend fun editTask(
        id: String,
        taskRequest: TaskRequest
    ) = taskService.editTask(
        id = id,
        taskRequest = taskRequest
    )

    suspend fun deleteTask(id: String) = taskService.deleteTask(id)
}