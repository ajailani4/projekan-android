package com.ajailani.projekan.domain.use_case

import com.ajailani.projekan.domain.repository.AuthRepository
import javax.inject.Inject

class RegisterAccountUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(
        name: String,
        email: String,
        username: String,
        password: String
    ) = authRepository.register(
        name = name,
        email = email,
        username = username,
        password = password
    )
}