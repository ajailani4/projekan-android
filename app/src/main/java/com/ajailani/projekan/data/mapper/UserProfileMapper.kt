package com.ajailani.projekan.data.mapper

import com.ajailani.projekan.data.remote.dto.UserProfileDto
import com.ajailani.projekan.domain.model.UserProfile

fun UserProfileDto.toUserProfile() =
    UserProfile(
        name = name,
        avatar = avatar
    )