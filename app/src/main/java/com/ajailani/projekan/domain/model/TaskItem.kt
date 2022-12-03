package com.ajailani.projekan.domain.model

import com.ajailani.projekan.util.TaskStatus
import com.squareup.moshi.Json

data class TaskItem(
    @field:Json(name = "_id")
    val id: String,
    val projectItem: String,
    val title: String,
    val status: TaskStatus
)