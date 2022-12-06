package com.ajailani.projekan.domain.use_case.project

import com.ajailani.projekan.domain.repository.ProjectRepository
import java.io.File
import javax.inject.Inject

class AddProjectUseCase @Inject constructor(
    private val projectRepository: ProjectRepository
) {
    operator fun invoke(
        title: String,
        description: String,
        platform: String,
        category: String,
        deadline: String,
        icon: File
    ) = projectRepository.addProject(
        title = title,
        description = description,
        platform = platform,
        category = category,
        deadline = deadline,
        icon = icon
    )
}