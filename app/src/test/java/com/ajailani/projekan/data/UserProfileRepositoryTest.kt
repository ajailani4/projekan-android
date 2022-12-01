package com.ajailani.projekan.data

import android.content.Context
import com.ajailani.projekan.data.remote.data_source.UserProfileRemoteDataSource
import com.ajailani.projekan.data.remote.dto.response.BaseResponse
import com.ajailani.projekan.data.repository.UserProfileRepositoryImpl
import com.ajailani.projekan.domain.model.UserProfile
import com.ajailani.projekan.domain.repository.UserProfileRepository
import com.ajailani.projekan.util.userProfile
import com.ajailani.projekan.util.userProfileDto
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn
import retrofit2.Response

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class UserProfileRepositoryTest {

    @Mock
    private lateinit var userProfileRemoteDataSource: UserProfileRemoteDataSource

    @Mock
    private lateinit var context: Context

    private lateinit var userProfileRepository: UserProfileRepository

    @Before
    fun setUp() {
        userProfileRepository = UserProfileRepositoryImpl(
            userProfileRemoteDataSource,
            context
        )
    }

    @Test
    fun `Get user profile should return success`() =
        runTest(UnconfinedTestDispatcher()) {
            val response = Response.success(
                200,
                BaseResponse(
                    code = 200,
                    status = "OK",
                    data = userProfileDto
                )
            )

            doReturn(response).`when`(userProfileRemoteDataSource).getProfile()

            val actualResource = userProfileRepository.getUserProfile().first()

            assertEquals(
                "Resource should be success",
                Resource.Success(userProfile),
                actualResource
            )
        }

    @Test
    fun `Get user profile should return fail`() =
        runTest(UnconfinedTestDispatcher()) {
            val response = Response.error<UserProfile>(
                400,
                "".toResponseBody()
            )

            doReturn(response).`when`(userProfileRemoteDataSource).getProfile()

            val actualResource = userProfileRepository.getUserProfile().first()

            assertEquals(
                "Resource should be error",
                Resource.Error<UserProfile>(),
                actualResource
            )
        }
}