package com.ajailani.projekan.domain.model

import com.ajailani.projekan.util.TaskStatus

data class TaskItem(
    val id: String,
    val projectId: String,
    val title: String,
    val status: TaskStatus
)