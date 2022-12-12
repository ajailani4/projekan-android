package com.ajailani.projekan.ui.feature.splash

import androidx.lifecycle.ViewModel
import com.ajailani.projekan.domain.use_case.user_credential.GetAccessTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getAccessTokenUseCase: GetAccessTokenUseCase
) : ViewModel() {
    fun getAccessToken() = getAccessTokenUseCase()
}