package com.ajailani.projekan.domain.use_case.task

import com.ajailani.projekan.domain.repository.TaskRepository
import javax.inject.Inject

class AddTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) {
    operator fun invoke(
        projectId: String,
        title: String
    ) = taskRepository.addTask(
        projectId = projectId,
        title = title
    )
}