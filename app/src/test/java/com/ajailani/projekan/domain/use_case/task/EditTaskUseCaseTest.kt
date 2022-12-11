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
class EditTaskUseCaseTest {
    private lateinit var taskRepositoryFake: TaskRepositoryFake
    private lateinit var editTaskUseCase: EditTaskUseCase

    @Before
    fun setUp() {
        taskRepositoryFake = TaskRepositoryFake()
        editTaskUseCase = EditTaskUseCase(taskRepositoryFake)
    }

    @Test
    fun `Edit task should return success`() =
        runTest(UnconfinedTestDispatcher()) {
            taskRepositoryFake.setResourceType(ResourceType.Success)

            val actualResource = editTaskUseCase(
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
    fun `Add task should return fail`() =
        runTest(UnconfinedTestDispatcher()) {
            taskRepositoryFake.setResourceType(ResourceType.Error)

            val actualResource = editTaskUseCase(
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