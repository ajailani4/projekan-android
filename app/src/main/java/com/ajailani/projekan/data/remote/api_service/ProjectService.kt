package com.ajailani.projekan.data.remote.api_service

import com.ajailani.projekan.data.remote.dto.ProjectDto
import com.ajailani.projekan.data.remote.dto.response.BaseResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ProjectService {
    @GET("projects")
    suspend fun getProjects(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("type") type: String?
    ): Response<BaseResponse<List<ProjectDto>>>
}