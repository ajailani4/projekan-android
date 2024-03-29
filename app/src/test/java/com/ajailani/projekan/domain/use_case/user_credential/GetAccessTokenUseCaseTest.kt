package com.ajailani.projekan.domain.use_case.user_credential

import com.ajailani.projekan.domain.repository.UserCredentialRepositoryFake
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetAccessTokenUseCaseTest {
    private lateinit var userCredentialRepositoryFake: UserCredentialRepositoryFake
    private lateinit var getAccessTokenUseCase: GetAccessTokenUseCase

    @Before
    fun setUp() {
        userCredentialRepositoryFake = UserCredentialRepositoryFake()
        getAccessTokenUseCase = GetAccessTokenUseCase(userCredentialRepositoryFake)
    }

    @Test
    fun `Get access token should not be empty`() =
        runTest(UnconfinedTestDispatcher()) {
            userCredentialRepositoryFake.saveAccessToken("abcd")

            val actualResult = getAccessTokenUseCase().first()

            assertEquals("Access token should be 'abcd'", "abcd", actualResult)
        }

    @Test
    fun `Get access token should be empty`() =
        runTest(UnconfinedTestDispatcher()) {
            userCredentialRepositoryFake.saveAccessToken("")

            val actualResult = getAccessTokenUseCase().first()

            assertEquals("Access token should be ''", "", actualResult)
        }
}