package com.ajailani.projekan.domain.repository

import androidx.paging.PagingData
import com.ajailani.projekan.data.Resource
import com.ajailani.projekan.domain.model.Project
import com.ajailani.projekan.domain.model.ProjectItem
import com.ajailani.projekan.util.ProjectType
import kotlinx.coroutines.flow.Flow
import java.io.File

interface ProjectRepository {
    fun getProjects(
        page: Int,
        size: Int,
        type: ProjectType?
    ): Flow<Resource<List<ProjectItem>>>

    fun getPagingProjects(
        type: ProjectType?
    ): Flow<PagingData<ProjectItem>>

    fun getProjectDetail(id: String): Flow<Resource<Project>>

    fun addProject(
        title: String,
        description: String,
        platform: String,
        category: String,
        deadline: String,
        icon: File
    ): Flow<Resource<Any>>
}