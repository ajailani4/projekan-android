package com.ajailani.projekan.util

import com.ajailani.projekan.data.remote.dto.UserCredentialDto
import com.ajailani.projekan.domain.model.Project
import com.ajailani.projekan.domain.model.UserCredential
import com.ajailani.projekan.domain.model.UserProfile

val userCredentialDto = UserCredentialDto(
    username = "george",
    accessToken = "abcd"
)

val userCredential = UserCredential(accessToken = "abcd")

val userProfile = UserProfile(
    name = "George",
    avatar = "image.jpg"
)

val projects = listOf(
    Project(
        id = "1",
        title = "Projekan",
        description = "Project management app",
        platform = "Mobile",
        category = "Application",
        deadline = "2022-12-05",
        icon = "icon.png"
    ),
    Project(
        id = "2",
        title = "Projekan 2",
        description = "Project management app",
        platform = "Mobile",
        category = "Application",
        deadline = "2022-12-05",
        icon = "icon.png"
    )
)