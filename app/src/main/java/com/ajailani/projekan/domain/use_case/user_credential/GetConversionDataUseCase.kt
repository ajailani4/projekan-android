package com.ajailani.projekan.domain.use_case.user_credential

import com.ajailani.projekan.domain.repository.UserCredentialRepository
import javax.inject.Inject

class GetConversionDataUseCase @Inject constructor(
    private val userCredentialRepository: UserCredentialRepository
) {
    operator fun invoke() = userCredentialRepository.getConversionData()
}