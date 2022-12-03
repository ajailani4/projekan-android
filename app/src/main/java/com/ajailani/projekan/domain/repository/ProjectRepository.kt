package com.ajailani.projekan.domain.repository

import androidx.paging.PagingData
import com.ajailani.projekan.data.Resource
import com.ajailani.projekan.domain.model.ProjectItem
import com.ajailani.projekan.util.ProjectType
import kotlinx.coroutines.flow.Flow

interface ProjectRepository {
    fun getProjects(
        page: Int,
        size: Int,
        type: ProjectType?
    ): Flow<Resource<List<ProjectItem>>>

    fun getPagingProjects(
        type: ProjectType?
    ): Flow<PagingData<ProjectItem>>
}