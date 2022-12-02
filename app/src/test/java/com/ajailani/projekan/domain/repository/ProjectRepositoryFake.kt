package com.ajailani.projekan.domain.repository

import androidx.paging.PagingData
import com.ajailani.projekan.data.Resource
import com.ajailani.projekan.domain.model.Project
import com.ajailani.projekan.util.ProjectType
import com.ajailani.projekan.util.ResourceType
import com.ajailani.projekan.util.projects
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class ProjectRepositoryFake : ProjectRepository {
    private lateinit var resourceType: ResourceType

    override fun getProjects(
        page: Int,
        size: Int,
        type: ProjectType?
    ): Flow<Resource<List<Project>>> =
        when (resourceType) {
            ResourceType.Success -> flowOf(Resource.Success(projects))

            ResourceType.Error -> flowOf(Resource.Error(null))
        }

    override fun getPagingProjects(type: ProjectType?): Flow<PagingData<Project>> {
        TODO("Not yet implemented")
    }

    fun setResourceType(type: ResourceType) {
        resourceType = type
    }
}