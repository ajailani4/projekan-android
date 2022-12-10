package com.ajailani.projekan.data.repository

import android.content.Context
import com.ajailani.projekan.R
import com.ajailani.projekan.data.Resource
import com.ajailani.projekan.data.remote.data_source.TaskRemoteDataSource
import com.ajailani.projekan.data.remote.dto.request.TaskRequest
import com.ajailani.projekan.domain.repository.TaskRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val taskRemoteDataSource: TaskRemoteDataSource,
    @ApplicationContext private val context: Context
) : TaskRepository {
    override fun addTask(projectId: String, title: String) =
        flow {
            val response = taskRemoteDataSource.addTask(
                TaskRequest(
                    projectId = projectId,
                    title = title
                )
            )

            when (response.code()) {
                201 -> emit(Resource.Success(response.body()?.data))

                else -> emit(Resource.Error(context.getString(R.string.something_wrong_happened)))
            }
        }
}