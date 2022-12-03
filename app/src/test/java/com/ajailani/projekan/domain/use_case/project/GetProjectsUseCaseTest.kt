package com.ajailani.projekan.domain.use_case.project

import com.ajailani.projekan.data.Resource
import com.ajailani.projekan.domain.model.ProjectItem
import com.ajailani.projekan.domain.repository.ProjectRepositoryFake
import com.ajailani.projekan.util.ResourceType
import com.ajailani.projekan.util.projects
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetProjectsUseCaseTest {
    private lateinit var projectRepositoryFake: ProjectRepositoryFake
    private lateinit var getProjectsUseCase: GetProjectsUseCase

    @Before
    fun setUp() {
        projectRepositoryFake = ProjectRepositoryFake()
        getProjectsUseCase = GetProjectsUseCase(projectRepositoryFake)
    }

    @Test
    fun `Get projects should return success`() =
        runTest(UnconfinedTestDispatcher()) {
            projectRepositoryFake.setResourceType(ResourceType.Success)

            val actualResource = getProjectsUseCase(
                page = 1,
                size = 10
            ).first()

            assertEquals(
                "Resource should be success",
                Resource.Success(projects),
                actualResource
            )
        }

    @Test
    fun `Get projects should return fail`() =
        runTest(UnconfinedTestDispatcher()) {
            projectRepositoryFake.setResourceType(ResourceType.Error)

            val actualResource = getProjectsUseCase(
                page = 1,
                size = 10
            ).first()

            assertEquals(
                "Resource should be error",
                Resource.Error<List<ProjectItem>>(),
                actualResource
            )
        }
}