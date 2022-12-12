package com.ajailani.projekan.data.remote.api_service

import com.ajailani.projekan.data.remote.dto.request.TaskRequest
import com.ajailani.projekan.data.remote.dto.response.BaseResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TaskService {
    @POST("tasks")
    suspend fun addTask(@Body taskRequest: TaskRequest): Response<BaseResponse<Any>>

    @PUT("tasks/{id}")
    suspend fun editTask(
        @Path("id") id: String,
        @Body taskRequest: TaskRequest
    ): Response<BaseResponse<Any>>

    @DELETE("tasks/{id}")
    suspend fun deleteTask(
        @Path("id") id: String
    ): Response<BaseResponse<Any>>
}