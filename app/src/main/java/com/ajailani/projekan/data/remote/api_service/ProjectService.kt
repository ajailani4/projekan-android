package com.ajailani.projekan.data.remote.api_service

import com.ajailani.projekan.data.remote.dto.ProjectDto
import com.ajailani.projekan.data.remote.dto.ProjectItemDto
import com.ajailani.projekan.data.remote.dto.response.BaseResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface ProjectService {
    @GET("projects")
    suspend fun getProjects(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("type") type: String?
    ): Response<BaseResponse<List<ProjectItemDto>>>

    @GET("projects/{id}")
    suspend fun getProjectDetail(
        @Path("id") id: String
    ): Response<BaseResponse<ProjectDto>>

    @Multipart
    @POST("projects")
    suspend fun addProject(
        @Part("title") title: RequestBody,
        @Part("description") description: RequestBody,
        @Part("platform") platform: RequestBody,
        @Part("category") category: RequestBody,
        @Part("deadline") deadline: RequestBody,
        @Part icon: MultipartBody.Part?
    ): Response<BaseResponse<Any>>

    @Multipart
    @PUT("projects/{id}")
    suspend fun editProject(
        @Path("id") id: String,
        @Part("title") title: RequestBody,
        @Part("description") description: RequestBody,
        @Part("platform") platform: RequestBody,
        @Part("category") category: RequestBody,
        @Part("deadline") deadline: RequestBody,
        @Part icon: MultipartBody.Part?
    ): Response<BaseResponse<Any>>

    @DELETE("projects/{id}")
    suspend fun deleteProject(
        @Path("id") id: String
    ): Response<BaseResponse<Any>>
}