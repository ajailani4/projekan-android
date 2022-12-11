package com.ajailani.projekan.data

import android.content.Context
import com.ajailani.projekan.data.remote.data_source.TaskRemoteDataSource
import com.ajailani.projekan.data.remote.dto.response.BaseResponse
import com.ajailani.projekan.data.repository.TaskRepositoryImpl
import com.ajailani.projekan.domain.repository.TaskRepository
import com.ajailani.projekan.util.ResourceType
import com.ajailani.projekan.util.TaskStatus
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import retrofit2.Response

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class TaskRepositoryTest {

    @Mock
    private lateinit var taskRemoteDataSource: TaskRemoteDataSource

    @Mock
    private lateinit var context: Context

    private lateinit var taskRepository: TaskRepository

    @Before
    fun setUp() {
        taskRepository = TaskRepositoryImpl(taskRemoteDataSource, context)
    }

    @Test
    fun `Add task should return success`() =
        runTest(UnconfinedTestDispatcher()) {
            val response = Response.success(
                201,
                BaseResponse(
                    code = 201,
                    status = "Created",
                    data = null
                )
            )

            doReturn(response).`when`(taskRemoteDataSource).addTask(any())

            val actualResource = taskRepository.addTask(
                projectId = "a1b2c3",
                title = "Task 1"
            ).first()

            val isSuccess = when (actualResource) {
                is Resource.Success -> true

                is Resource.Error -> false
            }

            assertEquals(
                "Resource should be success",
                true,
                isSuccess
            )
        }

    @Test
    fun `Add task should return fail`() =
        runTest(UnconfinedTestDispatcher()) {
            val response = Response.error<Any>(
                400,
                "".toResponseBody()
            )

            doReturn(response).`when`(taskRemoteDataSource).addTask(any())

            val actualResource = taskRepository.addTask(
                projectId = "a1b2c3",
                title = "Task 1"
            ).first()

            val isSuccess = when (actualResource) {
                is Resource.Success -> true

                is Resource.Error -> false
            }

            assertEquals(
                "Resource should be fail",
                false,
                isSuccess
            )
        }

    @Test
    fun `Edit task should return success`() =
        runTest(UnconfinedTestDispatcher()) {
            val response = Response.success(
                200,
                BaseResponse(
                    code = 200,
                    status = "OK",
                    data = null
                )
            )

            doReturn(response).`when`(taskRemoteDataSource).editTask(
                id = ArgumentMatchers.anyString(),
                taskRequest = any()
            )

            val actualResource = taskRepository.editTask(
                id = "a1b2c3",
                title = "Task 1",
                status = TaskStatus.UNDONE
            ).first()

            val isSuccess = when (actualResource) {
                is Resource.Success -> true

                is Resource.Error -> false
            }

            assertEquals(
                "Resource should be success",
                true,
                isSuccess
            )
        }

    @Test
    fun `Edit task should return fail`() =
        runTest(UnconfinedTestDispatcher()) {
            val response = Response.error<Any>(
                400,
                "".toResponseBody()
            )

            doReturn(response).`when`(taskRemoteDataSource).editTask(
                id = ArgumentMatchers.anyString(),
                taskRequest = any()
            )

            val actualResource = taskRepository.editTask(
                id = "a1b2c3",
                title = "Task 1",
                status = TaskStatus.UNDONE
            ).first()

            val isSuccess = when (actualResource) {
                is Resource.Success -> true

                is Resource.Error -> false
            }

            assertEquals(
                "Resource should be error",
                false,
                isSuccess
            )
        }
}