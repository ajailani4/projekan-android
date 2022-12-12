package com.ajailani.projekan.data.remote.dto.request

import com.ajailani.projekan.util.TaskStatus

data class TaskRequest(
    val projectId: String? = null,
    val title: String,
    val status: TaskStatus? = null
)
