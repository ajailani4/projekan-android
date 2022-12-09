package com.ajailani.projekan.domain.use_case.project

import com.ajailani.projekan.domain.repository.ProjectRepository
import java.io.File
import javax.inject.Inject

class EditProjectUseCase @Inject constructor(
    private val projectRepository: ProjectRepository
) {
    operator fun invoke(
        id: String,
        title: String,
        description: String,
        platform: String,
        category: String,
        deadline: String,
        icon: File?
    ) = projectRepository.editProject(
        id = id,
        title = title,
        description = description,
        platform = platform,
        category = category,
        deadline = deadline,
        icon = icon
    )
}