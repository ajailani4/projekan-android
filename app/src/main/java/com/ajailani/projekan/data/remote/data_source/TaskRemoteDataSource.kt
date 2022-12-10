package com.ajailani.projekan.data.remote.data_source

import com.ajailani.projekan.data.remote.api_service.TaskService
import com.ajailani.projekan.data.remote.dto.request.TaskRequest
import javax.inject.Inject

class TaskRemoteDataSource @Inject constructor(
    private val taskService: TaskService
) {
    fun addTask(taskRequest: TaskRequest) = taskService.addTask(taskRequest)
}