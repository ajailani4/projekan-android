package com.ajailani.projekan.data.remote.dto

import com.ajailani.projekan.util.TaskStatus
import com.squareup.moshi.Json

data class TaskItemDto(
    @field:Json(name = "_id")
    val id: String,
    val projectId: String,
    val title: String,
    val status: TaskStatus
)