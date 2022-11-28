package com.ajailani.projekan.domain.use_case.project

import com.ajailani.projekan.domain.repository.ProjectRepository
import com.ajailani.projekan.util.ProjectType
import javax.inject.Inject

class GetProjectsUseCase @Inject constructor(
    private val projectRepository: ProjectRepository
) {
    operator fun invoke(
        page: Int,
        size: Int,
        type: ProjectType? = null
    ) = projectRepository.getProjects(
        page = page,
        size = size,
        type = type
    )
}