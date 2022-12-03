package com.ajailani.projekan.data

import android.content.Context
import com.ajailani.projekan.data.remote.data_source.ProjectRemoteDataSource
import com.ajailani.projekan.data.remote.dto.response.BaseResponse
import com.ajailani.projekan.data.repository.ProjectRepositoryImpl
import com.ajailani.projekan.domain.model.ProjectItem
import com.ajailani.projekan.domain.repository.ProjectRepository
import com.ajailani.projekan.util.projectItems
import com.ajailani.projekan.util.projectItemsDto
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
                    data = projectItemsDto
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
                Resource.Success(projectItems),
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
}