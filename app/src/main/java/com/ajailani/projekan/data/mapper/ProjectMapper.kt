package com.ajailani.projekan.data.mapper

import com.ajailani.projekan.data.remote.dto.ProjectDto
import com.ajailani.projekan.domain.model.Project

fun ProjectDto.toProject() =
    Project(
        id = id,
        title = title,
        description = description,
        platform = platform,
        category = category,
        deadline = deadline,
        icon = icon
    )