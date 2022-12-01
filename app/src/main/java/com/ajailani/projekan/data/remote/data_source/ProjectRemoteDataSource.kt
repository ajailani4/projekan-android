package com.ajailani.projekan.data.remote.data_source

import com.ajailani.projekan.data.remote.api_service.ProjectService
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
}