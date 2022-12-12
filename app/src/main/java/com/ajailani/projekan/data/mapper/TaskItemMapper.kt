package com.ajailani.projekan.data.mapper

import com.ajailani.projekan.data.remote.dto.TaskItemDto
import com.ajailani.projekan.domain.model.TaskItem

fun TaskItemDto.toTaskItem() =
    TaskItem(
        id = id,
        projectId = projectId,
        title = title,
        status = status
    )