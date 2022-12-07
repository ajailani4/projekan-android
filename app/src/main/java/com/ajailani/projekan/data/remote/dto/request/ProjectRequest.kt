package com.ajailani.projekan.data.remote.dto.request

data class ProjectRequest(
    val title: String,
    val description: String,
    val platform: String,
    val category: String,
    val deadline: String,
    val icon: String
)
