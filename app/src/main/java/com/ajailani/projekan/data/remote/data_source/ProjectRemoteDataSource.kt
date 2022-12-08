package com.ajailani.projekan.data.remote.data_source

import com.ajailani.projekan.data.remote.api_service.ProjectService
import com.ajailani.projekan.data.remote.dto.response.BaseResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response
import java.io.File
import javax.inject.Inject

class ProjectRemoteDataSource @Inject constructor(
    private val projectService: ProjectService
) {
    suspend fun getProjects(
        page: Int,
        size: Int,
        type: String?
    ) = projectService.getProjects(
        page = page,
        size = size,
        type = type
    )

    suspend fun getProjectDetail(id: String) = projectService.getProjectDetail(id)

    suspend fun addProject(
        title: String,
        description: String,
        platform: String,
        category: String,
        deadline: String,
        icon: File?
    ): Response<BaseResponse<Any>> {
        val titlePart = title.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val descriptionPart = description.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val platformPart = platform.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val categoryPart = category.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val deadlinePart = deadline.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val iconPart = icon?.let {
            MultipartBody.Part.createFormData(
                "icon",
                icon.name,
                icon.asRequestBody("image/*".toMediaTypeOrNull())
            )
        }

        return projectService.addProject(
            title = titlePart,
            description = descriptionPart,
            platform = platformPart,
            category = categoryPart,
            deadline = deadlinePart,
            icon = iconPart
        )
    }
}