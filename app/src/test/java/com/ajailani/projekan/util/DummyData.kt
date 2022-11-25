package com.ajailani.projekan.util

import com.ajailani.projekan.data.remote.dto.UserCredentialDto
import com.ajailani.projekan.domain.model.UserCredential

val userCredentialDto = UserCredentialDto(
    username = "george",
    accessToken = "abcd"
)
val userCredential = UserCredential(accessToken = "abcd")