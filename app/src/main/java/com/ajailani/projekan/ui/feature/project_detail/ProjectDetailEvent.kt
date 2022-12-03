package com.ajailani.projekan.ui.feature.project_detail

import com.ajailani.projekan.domain.model.Project
import com.ajailani.projekan.domain.model.TaskItem

sealed class ProjectDetailEvent {
    object GetProjectDetail : ProjectDetailEvent()
    /*data class UpdateProjectDetail(val project: Project) : ProjectDetailEvent()
    data class DeleteProject(val id: String) : ProjectDetailEvent()
    data class AddTask(val taskItem: TaskItem) : ProjectDetailEvent()
    data class UpdateTask(val taskItem: TaskItem) : ProjectDetailEvent()
    data class DeleteTask(val id: String) : ProjectDetailEvent()*/
}