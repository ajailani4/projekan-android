package com.ajailani.projekan.ui.viewmodel

import com.ajailani.projekan.domain.use_case.user_credential.GetAccessTokenUseCase
import com.ajailani.projekan.ui.feature.splash.SplashViewModel
import com.ajailani.projekan.util.TestCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class SplashViewModelTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var getAccessTokenUseCase: GetAccessTokenUseCase

    private lateinit var splashViewModel: SplashViewModel

    @Before
    fun setUp() {
        splashViewModel = SplashViewModel(getAccessTokenUseCase)
    }

    @Test
    fun `Get access token should not be empty`() {
        testCoroutineRule.runTest {
            val accessToken = flowOf("abcd")

            doReturn(accessToken).`when`(getAccessTokenUseCase)()

            val actualAccessToken = splashViewModel.getAccessToken().first()

            assertEquals("Accesss token should be 'abcd'", "abcd", actualAccessToken)
        }
    }

    @Test
    fun `Get access token should be empty`() {
        testCoroutineRule.runTest {
            val accessToken = flowOf("")

            doReturn(accessToken).`when`(getAccessTokenUseCase)()

            val actualAccessToken = splashViewModel.getAccessToken().first()

            assertEquals("Accesss token should be ''", "", actualAccessToken)
        }
    }
}