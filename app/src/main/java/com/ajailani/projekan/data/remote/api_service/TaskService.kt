package com.ajailani.projekan.data.remote.api_service

import com.ajailani.projekan.data.remote.dto.request.TaskRequest
import com.ajailani.projekan.data.remote.dto.response.BaseResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface TaskService {
    @POST("tasks")
    suspend fun addTask(@Body taskRequest: TaskRequest): Response<BaseResponse<Any>>
}