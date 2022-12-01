package com.ajailani.projekan.data

import com.ajailani.projekan.data.local.PreferencesDataStore
import com.ajailani.projekan.data.repository.UserCredentialRepositoryImpl
import com.ajailani.projekan.domain.repository.UserCredentialRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class UserCredentialRepositoryTest {

    @Mock
    private lateinit var preferencesDataStore: PreferencesDataStore

    private lateinit var userCredentialRepository: UserCredentialRepository

    @Before
    fun setUp() {
        userCredentialRepository = UserCredentialRepositoryImpl(preferencesDataStore)
    }

    @Test
    fun `Get access token should not be empty`() =
        runBlocking {
            val accessToken = flowOf("abcd")

            doReturn(accessToken).`when`(preferencesDataStore).getAccessToken()

            val actualAccessToken = userCredentialRepository.getAccessToken().first()

            assertEquals("Access token should be 'abcd'", "abcd", actualAccessToken)
        }

    @Test
    fun `Get access token should be empty`() =
        runBlocking {
            val accessToken = flowOf("")

            doReturn(accessToken).`when`(preferencesDataStore).getAccessToken()

            val actualAccessToken = userCredentialRepository.getAccessToken().first()

            assertEquals("Access token should be ''", "", actualAccessToken)
        }
}