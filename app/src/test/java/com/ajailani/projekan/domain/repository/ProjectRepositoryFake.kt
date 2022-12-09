package com.ajailani.projekan.domain.repository

import androidx.paging.PagingData
import com.ajailani.projekan.data.Resource
import com.ajailani.projekan.domain.model.Project
import com.ajailani.projekan.domain.model.ProjectItem
import com.ajailani.projekan.util.ProjectType
import com.ajailani.projekan.util.ResourceType
import com.ajailani.projekan.util.project
import com.ajailani.projekan.util.projects
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.io.File

class ProjectRepositoryFake : ProjectRepository {
    private lateinit var resourceType: ResourceType

    override fun getProjects(
        page: Int,
        size: Int,
        type: ProjectType?
    ): Flow<Resource<List<ProjectItem>>> =
        when (resourceType) {
            ResourceType.Success -> flowOf(Resource.Success(projects))

            ResourceType.Error -> flowOf(Resource.Error(null))
        }

    override fun getPagingProjects(type: ProjectType?): Flow<PagingData<ProjectItem>> {
        TODO("Not yet implemented")
    }

    override fun getProjectDetail(id: String): Flow<Resource<Project>> =
        when (resourceType) {
            ResourceType.Success -> flowOf(Resource.Success(project))

            ResourceType.Error -> flowOf(Resource.Error(null))
        }

    override fun addProject(
        title: String,
        description: String,
        platform: String,
        category: String,
        deadline: String,
        icon: File?
    ): Flow<Resource<Any>> =
        when (resourceType) {
            ResourceType.Success -> flowOf(Resource.Success(Any()))

            ResourceType.Error -> flowOf(Resource.Error(null))
        }

    override fun editProject(
        id: String,
        title: String,
        description: String,
        platform: String,
        category: String,
        deadline: String,
        icon: File?
    ): Flow<Resource<Any>> =
        when (resourceType) {
            ResourceType.Success -> flowOf(Resource.Success(Any()))

            ResourceType.Error -> flowOf(Resource.Error(null))
        }

    override fun deleteProject(id: String): Flow<Resource<Any>> {
        TODO("Not yet implemented")
    }

    fun setResourceType(type: ResourceType) {
        resourceType = type
    }
}