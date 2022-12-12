package com.ajailani.projekan.domain.use_case.user_profile

import com.ajailani.projekan.domain.repository.UserProfileRepository
import javax.inject.Inject

class GetUserProfileUseCase @Inject constructor(
    private val userProfileRepository: UserProfileRepository
) {
    operator fun invoke() = userProfileRepository.getUserProfile()
}