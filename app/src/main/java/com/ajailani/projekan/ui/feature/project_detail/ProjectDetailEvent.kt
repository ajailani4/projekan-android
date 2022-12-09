package com.ajailani.projekan.ui.feature.project_detail

sealed class ProjectDetailEvent {
    object GetProjectDetail : ProjectDetailEvent()
    data class OnPullRefresh(val isRefreshing: Boolean) : ProjectDetailEvent()
    data class OnActionMenuClicked(val actionMenu: Int) : ProjectDetailEvent()
    /*data class DeleteProject(val id: String) : ProjectDetailEvent()
    data class AddTask(val taskItem: TaskItem) : ProjectDetailEvent()
    data class EditTask(val taskItem: TaskItem) : ProjectDetailEvent()
    data class DeleteTask(val id: String) : ProjectDetailEvent()*/
}