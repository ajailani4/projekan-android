package com.ajailani.projekan.domain.model

data class ProjectItem(
    val id: String,
    val title: String,
    val description: String,
    val platform: String,
    val category: String,
    val deadline: String,
    val icon: String
)
