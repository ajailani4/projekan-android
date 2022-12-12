package com.ajailani.projekan.data.mapper

import com.ajailani.projekan.data.remote.dto.UserCredentialDto
import com.ajailani.projekan.domain.model.UserCredential

fun UserCredentialDto.toUserCredential() =
    UserCredential(accessToken = accessToken)