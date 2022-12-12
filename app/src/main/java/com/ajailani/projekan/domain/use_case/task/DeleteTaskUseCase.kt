package com.ajailani.projekan.domain.use_case.task

import com.ajailani.projekan.domain.repository.TaskRepository
import javax.inject.Inject

class DeleteTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) {
    operator fun invoke(id: String) = taskRepository.deleteTask(id)
}