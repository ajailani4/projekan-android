package com.ajailani.projekan.domain.use_case.task

import com.ajailani.projekan.domain.repository.TaskRepository
import com.ajailani.projekan.util.TaskStatus
import javax.inject.Inject

class EditTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) {
    operator fun invoke(
        id: String,
        title: String,
        status: TaskStatus
    ) = taskRepository.editTask(
        id = id,
        title = title,
        status = status
    )
}