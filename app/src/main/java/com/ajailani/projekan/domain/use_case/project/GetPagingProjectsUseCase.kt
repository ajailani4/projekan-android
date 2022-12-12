package com.ajailani.projekan.domain.use_case.project

import com.ajailani.projekan.domain.repository.ProjectRepository
import com.ajailani.projekan.util.ProjectType
import javax.inject.Inject

class GetPagingProjectsUseCase @Inject constructor(
    private val projectRepository: ProjectRepository
) {
    operator fun invoke(type: ProjectType? = null) = projectRepository.getPagingProjects(type)
}