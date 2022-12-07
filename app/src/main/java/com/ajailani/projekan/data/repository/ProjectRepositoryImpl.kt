package com.ajailani.projekan.data.repository

import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.map
import com.ajailani.projekan.R
import com.ajailani.projekan.data.Resource
import com.ajailani.projekan.data.mapper.toProject
import com.ajailani.projekan.data.remote.data_source.PagingDataSource
import com.ajailani.projekan.data.remote.data_source.ProjectRemoteDataSource
import com.ajailani.projekan.domain.repository.ProjectRepository
import com.ajailani.projekan.util.ProjectType
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.io.File
import javax.inject.Inject

class ProjectRepositoryImpl @Inject constructor(
    private val projectRemoteDataSource: ProjectRemoteDataSource,
    @ApplicationContext private val context: Context
) : ProjectRepository {
    override fun getProjects(
        page: Int,
        size: Int,
        type: ProjectType?
    ) = flow {
        val response = projectRemoteDataSource.getProjects(
            page = page,
            size = size,
            type = type.toString()
        )

        when (response.code()) {
            200 -> emit(
                Resource.Success(
                    response.body()?.data?.map { projectDto -> projectDto.toProject() }
                )
            )

            else -> emit(Resource.Error(context.getString(R.string.something_wrong_happened)))
        }
    }

    override fun getPagingProjects(type: ProjectType?) =
        Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = 10),
            pagingSourceFactory = {
                PagingDataSource { page, size ->
                    projectRemoteDataSource.getProjects(
                        page = page,
                        size = size,
                        type = type.toString()
                    )
                }
            }
        ).flow.map {
            it.map { projectDto -> projectDto.toProject() }
        }

    override fun getProjectDetail(id: String) =
        flow {
            val response = projectRemoteDataSource.getProjectDetail(id)

            when (response.code()) {
                200 -> emit(Resource.Success(response.body()?.data?.toProject()))

                else -> emit(Resource.Error(context.getString(R.string.something_wrong_happened)))
            }
        }

    override fun addProject(
        title: String,
        description: String,
        platform: String,
        category: String,
        deadline: String,
        icon: File
    ) =
        flow {
            val response = projectRemoteDataSource.addProject(
                title = title,
                description = description,
                platform = platform,
                category = category,
                deadline = deadline,
                icon = icon
            )

            when (response.code()) {
                201 -> emit(Resource.Success(response.body()?.data))

                413 -> emit(Resource.Error(context.getString(R.string.photo_size_is_too_large)))

                else -> emit(Resource.Error(context.getString(R.string.something_wrong_happened)))
            }
        }
}