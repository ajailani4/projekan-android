package com.ajailani.projekan.data.remote.data_source

import com.ajailani.projekan.data.remote.api_service.ProjectService
import com.ajailani.projekan.util.ProjectType
import javax.inject.Inject

class PlantRemoteDataSource @Inject constructor(
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