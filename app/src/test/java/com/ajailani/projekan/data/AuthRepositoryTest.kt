package com.ajailani.projekan.data

import android.content.Context
import com.ajailani.projekan.data.remote.data_source.AuthRemoteDataSource
import com.ajailani.projekan.data.remote.dto.response.BaseResponse
import com.ajailani.projekan.data.repository.AuthRepositoryImpl
import com.ajailani.projekan.domain.repository.AuthRepository
import com.ajailani.projekan.util.userCredential
import com.ajailani.projekan.util.userCredentialDto
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import retrofit2.Response
import com.ajailani.projekan.R
import com.ajailani.projekan.domain.model.UserCredential

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class AuthRepositoryTest {

    @Mock
    private lateinit var authRemoteDataSource: AuthRemoteDataSource

    @Mock
    private lateinit var context: Context

    private lateinit var authRepository: AuthRepository

    @Before
    fun setUp() {
        authRepository = AuthRepositoryImpl(
            authRemoteDataSource,
            context
        )
    }

    @Test
    fun `Login should return success`() =
        runBlocking {
            val response = Response.success(
                200,
                BaseResponse(
                    code = 200,
                    status = "OK",
                    data = userCredentialDto
                )
            )

            doReturn(response).`when`(authRemoteDataSource).login(any())

            val actualResource = authRepository.login(
                username = "george",
                password = "123"
            ).first()

            assertEquals(
                "Resource should be success",
                Resource.Success(userCredential),
                actualResource
            )
        }

    @Test
    fun `Login should return fail`() =
        runBlocking {
            val response = Response.error<Unit>(
                401,
                "".toResponseBody()
            )

            doReturn(null).`when`(context).getString(R.string.incorrect_username_or_pass)
            doReturn(response).`when`(authRemoteDataSource).login(any())

            val actualResource = authRepository.login(
                username = "george",
                password = "123"
            ).first()

            assertEquals(
                "Resource should be fail",
                Resource.Error<UserCredential>(),
                actualResource
            )
        }
}