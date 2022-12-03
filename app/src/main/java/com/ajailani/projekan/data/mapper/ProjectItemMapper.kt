package com.ajailani.projekan.data.mapper

import com.ajailani.projekan.data.remote.dto.ProjectItemDto
import com.ajailani.projekan.domain.model.ProjectItem

fun ProjectItemDto.toProject() =
    ProjectItem(
        id = id,
        title = title,
        description = description,
        platform = platform,
        category = category,
        deadline = deadline,
        icon = icon
    )