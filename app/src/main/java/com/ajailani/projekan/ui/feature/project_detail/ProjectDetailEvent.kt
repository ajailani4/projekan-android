package com.ajailani.projekan.ui.feature.project_detail

sealed class ProjectDetailEvent {
    object GetProjectDetail : ProjectDetailEvent()
    object DeleteProject : ProjectDetailEvent()
    data class OnPullRefresh(val isRefreshing: Boolean) : ProjectDetailEvent()
    data class OnMoreMenuClicked(val actionMenu: Int) : ProjectDetailEvent()
    data class OnDeleteProjectDialogVisChanged(val isVisible: Boolean) : ProjectDetailEvent()
    /*data class AddTask(val taskItem: TaskItem) : ProjectDetailEvent()
    data class EditTask(val taskItem: TaskItem) : ProjectDetailEvent()
    data class DeleteTask(val id: String) : ProjectDetailEvent()*/
}