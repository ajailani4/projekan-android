package com.ajailani.projekan.domain.use_case.user_credential

import com.ajailani.projekan.domain.repository.UserCredentialRepositoryFake
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

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
        runBlocking {
            userCredentialRepositoryFake.saveAccessToken("abcd")

            val actualResult = getAccessTokenUseCase().first()

            assertEquals("Access token should be 'abcd'", "abcd", actualResult)
        }

    @Test
    fun `Get access token should be empty`() =
        runBlocking {
            userCredentialRepositoryFake.saveAccessToken("")

            val actualResult = getAccessTokenUseCase().first()

            assertEquals("Access token should be ''", "", actualResult)
        }
}