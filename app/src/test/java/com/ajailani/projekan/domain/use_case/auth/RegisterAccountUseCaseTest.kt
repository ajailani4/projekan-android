package com.ajailani.projekan.domain.use_case.auth

import com.ajailani.projekan.data.Resource
import com.ajailani.projekan.domain.model.UserCredential
import com.ajailani.projekan.domain.repository.AuthRepositoryFake
import com.ajailani.projekan.util.ResourceType
import com.ajailani.projekan.util.userCredential
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class RegisterAccountUseCaseTest {
    private lateinit var authRepositoryFake: AuthRepositoryFake
    private lateinit var registerAccountUseCase: RegisterAccountUseCase

    @Before
    fun setUp() {
        authRepositoryFake = AuthRepositoryFake()
        registerAccountUseCase = RegisterAccountUseCase(authRepositoryFake)
    }

    @Test
    fun `Register should return success`() =
        runTest(UnconfinedTestDispatcher()) {
            authRepositoryFake.setResourceType(ResourceType.Success)

            val actualResource = registerAccountUseCase(
                name = "George Zayvich",
                email = "george@email.com",
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
    fun `Register should return fail`() =
        runTest(UnconfinedTestDispatcher()) {
            authRepositoryFake.setResourceType(ResourceType.Error)

            val actualResource = registerAccountUseCase(
                name = "George Zayvich",
                email = "george@email.com",
                username = "george",
                password = "123"
            ).first()

            assertEquals(
                "Resource should be error",
                Resource.Error<UserCredential>(),
                actualResource
            )
        }
}