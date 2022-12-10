package com.ajailani.projekan.domain.use_case.task

import com.ajailani.projekan.data.Resource
import com.ajailani.projekan.domain.repository.TaskRepositoryFake
import com.ajailani.projekan.util.ResourceType
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class AddTaskUseCaseTest {
    private lateinit var taskRepositoryFake: TaskRepositoryFake
    private lateinit var addTaskUseCase: AddTaskUseCase

    @Before
    fun setUp() {
        taskRepositoryFake = TaskRepositoryFake()
        addTaskUseCase = AddTaskUseCase(taskRepositoryFake)
    }

    @Test
    fun `Add task should return success`() =
        runTest(UnconfinedTestDispatcher()) {
            taskRepositoryFake.setResourceType(ResourceType.Success)

            val actualResource = addTaskUseCase(
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
            taskRepositoryFake.setResourceType(ResourceType.Error)

            val actualResource = addTaskUseCase(
                projectId = "a1b2c3",
                title = "Task 1"
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