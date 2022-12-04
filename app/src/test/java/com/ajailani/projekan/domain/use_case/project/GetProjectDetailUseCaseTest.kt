package com.ajailani.projekan.domain.use_case.project

import com.ajailani.projekan.data.Resource
import com.ajailani.projekan.domain.model.Project
import com.ajailani.projekan.domain.repository.ProjectRepositoryFake
import com.ajailani.projekan.util.ResourceType
import com.ajailani.projekan.util.project
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetProjectDetailUseCaseTest {
    private lateinit var projectRepositoryFake: ProjectRepositoryFake
    private lateinit var getProjectDetailUseCase: GetProjectDetailUseCase

    @Before
    fun setUp() {
        projectRepositoryFake = ProjectRepositoryFake()
        getProjectDetailUseCase = GetProjectDetailUseCase(projectRepositoryFake)
    }

    @Test
    fun `Get project detail should return success`() =
        runTest(UnconfinedTestDispatcher()) {
            projectRepositoryFake.setResourceType(ResourceType.Success)

            val actualResource = getProjectDetailUseCase("a1b2c3").first()

            assertEquals(
                "Resource should be success",
                Resource.Success(project),
                actualResource
            )
        }

    @Test
    fun `Get project detail should return fail`() =
        runTest(UnconfinedTestDispatcher()) {
            projectRepositoryFake.setResourceType(ResourceType.Error)

            val actualResource = getProjectDetailUseCase("a1b2c3").first()

            assertEquals(
                "Resource should be error",
                Resource.Error<Project>(),
                actualResource
            )
        }
}