package com.ajailani.projekan.data.remote.dto

import com.ajailani.projekan.util.ProjectStatus

data class ProjectDto(
    val id: String,
    val title: String,
    val description: String,
    val platform: String,
    val category: String,
    val deadline: String,
    val icon: String,
    val progress: Int,
    val status: ProjectStatus,
    val tasks: List<TaskItemDto>
)
