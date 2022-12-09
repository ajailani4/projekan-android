package com.ajailani.projekan.domain.use_case.project

import com.ajailani.projekan.domain.repository.ProjectRepository
import javax.inject.Inject

class DeleteProjectUseCase @Inject constructor(
    private val projectRepository: ProjectRepository
) {
    operator fun invoke(id: String) = projectRepository.deleteProject(id)
}