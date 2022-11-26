package com.ajailani.projekan.ui.feature.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ajailani.projekan.data.Resource
import com.ajailani.projekan.domain.use_case.RegisterAccountUseCase
import com.ajailani.projekan.ui.common.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerAccountUseCase: RegisterAccountUseCase
) : ViewModel() {
    var registerState by mutableStateOf<UIState<Nothing>>(UIState.Idle)
        private set

    var name by mutableStateOf("")
        private set

    var email by mutableStateOf("")
        private set

    var username by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var passwordVisibility by mutableStateOf(false)
        private set

    fun onEvent(event: RegisterEvent) {
        when (event) {
            RegisterEvent.Register -> register()

            is RegisterEvent.OnNameChanged -> name = event.name

            is RegisterEvent.OnEmailChanged -> email = event.email

            is RegisterEvent.OnUsernameChanged -> username = event.username

            is RegisterEvent.OnPasswordChanged -> password = event.password

            RegisterEvent.OnPasswordVisibilityChanged -> passwordVisibility = !passwordVisibility
        }
    }

    private fun register() {
        registerState = UIState.Loading

        viewModelScope.launch {
            registerAccountUseCase(
                name = name,
                email = email,
                username = username,
                password = password
            ).catch {
                registerState = UIState.Error(it.localizedMessage)
            }.collect {
                registerState = when (it) {
                    is Resource.Success -> {
                        UIState.Success(null)
                    }

                    is Resource.Error -> {
                        UIState.Error(it.message)
                    }
                }
            }
        }
    }
}