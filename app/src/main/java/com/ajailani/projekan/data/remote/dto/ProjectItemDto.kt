package com.ajailani.projekan.data.remote.dto

data class ProjectItemDto(
    val id: String,
    val title: String,
    val description: String,
    val platform: String,
    val category: String,
    val deadline: String,
    val icon: String
)
