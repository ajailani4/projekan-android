package com.ajailani.projekan.data

import android.content.Context
import com.ajailani.projekan.data.remote.data_source.ProjectRemoteDataSource
import com.ajailani.projekan.data.remote.dto.response.BaseResponse
import com.ajailani.projekan.data.repository.ProjectRepositoryImpl
import com.ajailani.projekan.domain.model.Project
import com.ajailani.projekan.domain.model.ProjectItem
import com.ajailani.projekan.domain.repository.ProjectRepository
import com.ajailani.projekan.util.project
import com.ajailani.projekan.util.projectDto
import com.ajailani.projekan.util.projects
import com.ajailani.projekan.util.projectsDto
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.isNull
import retrofit2.Response

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ProjectRepositoryTest {

    @Mock
    private lateinit var projectRemoteDataSource: ProjectRemoteDataSource

    @Mock
    private lateinit var context: Context

    private lateinit var projectRepository: ProjectRepository

    @Before
    fun setUp() {
        projectRepository = ProjectRepositoryImpl(
            projectRemoteDataSource,
            context
        )
    }

    @Test
    fun `Get projects should return success`() =
        runTest(UnconfinedTestDispatcher()) {
            val response = Response.success(
                200,
                BaseResponse(
                    code = 200,
                    status = "OK",
                    data = projectsDto
                )
            )

            doReturn(response).`when`(projectRemoteDataSource).getProjects(
                page = anyInt(),
                size = anyInt(),
                type = anyString()
            )

            val actualResource = projectRepository.getProjects(
                page = 1,
                size = 10,
                type = null
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
            val response = Response.error<List<ProjectItem>>(
                400,
                "".toResponseBody()
            )

            doReturn(response).`when`(projectRemoteDataSource).getProjects(
                page = anyInt(),
                size = anyInt(),
                type = anyString()
            )

            val actualResource = projectRepository.getProjects(
                page = 1,
                size = 10,
                type = null
            ).first()

            assertEquals(
                "Resource should be error",
                Resource.Error<List<ProjectItem>>(),
                actualResource
            )
        }

    @Test
    fun `Get project detail should return success`() =
        runTest(UnconfinedTestDispatcher()) {
            val response = Response.success(
                200,
                BaseResponse(
                    code = 200,
                    status = "OK",
                    data = projectDto
                )
            )

            doReturn(response).`when`(projectRemoteDataSource).getProjectDetail(anyString())

            val actualResource = projectRepository.getProjectDetail("a1b2c3").first()

            assertEquals(
                "Resource should be success",
                Resource.Success(project),
                actualResource
            )
        }

    @Test
    fun `Get project detail should return fail`() =
        runTest(UnconfinedTestDispatcher()) {
            val response = Response.error<Project>(
                400,
                "".toResponseBody()
            )

            doReturn(response).`when`(projectRemoteDataSource).getProjectDetail(anyString())

            val actualResource = projectRepository.getProjectDetail("a1b2c3").first()

            assertEquals(
                "Resource should be error",
                Resource.Error<Project>(),
                actualResource
            )
        }

    @Test
    fun `Add project should return success`() =
        runTest(UnconfinedTestDispatcher()) {
            val response = Response.success(
                201,
                BaseResponse(
                    code = 201,
                    status = "OK",
                    data = null
                )
            )

            doReturn(response).`when`(projectRemoteDataSource).addProject(
                title = anyString(),
                description = anyString(),
                platform = anyString(),
                category = anyString(),
                deadline = anyString(),
                icon = isNull()
            )

            val actualResource = projectRepository.addProject(
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
            val response = Response.error<Any>(
                400,
                "".toResponseBody()
            )

            doReturn(response).`when`(projectRemoteDataSource).addProject(
                title = anyString(),
                description = anyString(),
                platform = anyString(),
                category = anyString(),
                deadline = anyString(),
                icon = isNull()
            )

            val actualResource = projectRepository.addProject(
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