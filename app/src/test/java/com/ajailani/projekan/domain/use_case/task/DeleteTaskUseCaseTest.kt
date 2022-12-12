package com.ajailani.projekan.domain.use_case.task

import com.ajailani.projekan.data.Resource
import com.ajailani.projekan.domain.repository.TaskRepositoryFake
import com.ajailani.projekan.util.ResourceType
import com.ajailani.projekan.util.TaskStatus
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class DeleteTaskUseCaseTest {
    private lateinit var taskRepositoryFake: TaskRepositoryFake
    private lateinit var deleteTaskUseCase: DeleteTaskUseCase

    @Before
    fun setUp() {
        taskRepositoryFake = TaskRepositoryFake()
        deleteTaskUseCase = DeleteTaskUseCase(taskRepositoryFake)
    }

    @Test
    fun `Delete task should return success`() =
        runTest(UnconfinedTestDispatcher()) {
            taskRepositoryFake.setResourceType(ResourceType.Success)

            val isSuccess = when (deleteTaskUseCase("a1b2c3").first()) {
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
    fun `Delete task should return fail`() =
        runTest(UnconfinedTestDispatcher()) {
            taskRepositoryFake.setResourceType(ResourceType.Error)

            val isSuccess = when (deleteTaskUseCase("a1b2c3").first()) {
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