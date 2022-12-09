package com.ajailani.projekan.domain.use_case.project

import com.ajailani.projekan.data.Resource
import com.ajailani.projekan.domain.repository.ProjectRepositoryFake
import com.ajailani.projekan.util.ResourceType
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class EditProjectUseCaseTest {
    private lateinit var projectRepositoryFake: ProjectRepositoryFake
    private lateinit var editProjectUseCase: EditProjectUseCase

    @Before
    fun setUp() {
        projectRepositoryFake = ProjectRepositoryFake()
        editProjectUseCase = EditProjectUseCase(projectRepositoryFake)
    }

    @Test
    fun `Add project should return success`() =
        runTest(UnconfinedTestDispatcher()) {
            projectRepositoryFake.setResourceType(ResourceType.Success)

            val actualResource = editProjectUseCase(
                id = "a1b2c3",
                title = "Projekan",
                description = "Project management app",
                platform = "Mobile",
                category = "Application",
                deadline = "2022-12-05",
                icon = null
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
    fun `Add project should return fail`() =
        runTest(UnconfinedTestDispatcher()) {
            projectRepositoryFake.setResourceType(ResourceType.Error)

            val actualResource = editProjectUseCase(
                id = "a1b2c3",
                title = "Projekan",
                description = "Project management app",
                platform = "Mobile",
                category = "Application",
                deadline = "2022-12-05",
                icon = null
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