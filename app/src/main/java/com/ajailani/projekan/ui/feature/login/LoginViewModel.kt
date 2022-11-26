package com.ajailani.projekan.ui.feature.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ajailani.projekan.data.Resource
import com.ajailani.projekan.domain.use_case.LoginAccountUseCase
import com.ajailani.projekan.ui.common.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginAccountUseCase: LoginAccountUseCase
) : ViewModel() {
    var loginState by mutableStateOf<UIState<Nothing>>(UIState.Idle)
        private set

    var username by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var passwordVisibility by mutableStateOf(false)
        private set

    fun onEvent(event: LoginEvent) {
        when (event) {
            LoginEvent.LogIn -> login()

            is LoginEvent.OnUsernameChanged -> username = event.username

            is LoginEvent.OnPasswordChanged -> password = event.password

            LoginEvent.OnPasswordVisibilityChanged -> passwordVisibility = !passwordVisibility
        }
    }

    private fun login() {
        loginState = UIState.Loading

        viewModelScope.launch {
            loginAccountUseCase(
                username = username,
                password = password
            ).catch {
                loginState = UIState.Error(it.localizedMessage)
            }.collect {
                loginState = when (it) {
                    is Resource.Success -> {
                        UIState.Success(null)
                    }

                    is Resource.Error -> {
                        UIState.Fail(it.message)
                    }
                }
            }
        }
    }
}