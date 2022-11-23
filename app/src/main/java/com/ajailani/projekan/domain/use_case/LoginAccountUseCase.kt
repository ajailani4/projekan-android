package com.ajailani.projekan.domain.use_case

import com.ajailani.projekan.domain.repository.AuthRepository
import javax.inject.Inject

class LoginAccountUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
}