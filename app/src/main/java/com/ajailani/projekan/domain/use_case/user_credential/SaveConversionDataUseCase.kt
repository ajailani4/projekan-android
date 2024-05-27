package com.ajailani.projekan.domain.use_case.user_credential

import com.ajailani.projekan.domain.repository.UserCredentialRepository
import javax.inject.Inject

class SaveConversionDataUseCase @Inject constructor(
    private val userCredentialRepository: UserCredentialRepository
) {
    suspend operator fun invoke(conversionData: String) {
        userCredentialRepository.saveConversionData(conversionData)
    }
}