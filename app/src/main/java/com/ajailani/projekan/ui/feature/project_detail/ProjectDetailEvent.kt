package com.ajailani.projekan.ui.feature.project_detail

import com.ajailani.projekan.domain.model.TaskItem

sealed class ProjectDetailEvent {
    object GetProjectDetail : ProjectDetailEvent()
    object DeleteProject : ProjectDetailEvent()
    object AddTask : ProjectDetailEvent()
    object EditTask : ProjectDetailEvent()
    data class OnTaskTitleChanged(val taskTitle: String) : ProjectDetailEvent()
    data class OnTaskChecked(val index: Int, val task: TaskItem) : ProjectDetailEvent()
    data class OnTaskSelected(val task: TaskItem?) : ProjectDetailEvent()
    data class OnPullRefresh(val isRefreshing: Boolean) : ProjectDetailEvent()
    data class OnMoreMenuClicked(val actionMenu: Int) : ProjectDetailEvent()
    data class OnAddEditTaskSheetVisChanged(val isVisible: Boolean) : ProjectDetailEvent()
    data class OnDeleteProjectDialogVisChanged(val isVisible: Boolean) : ProjectDetailEvent()
    //data class DeleteTask(val id: String) : ProjectDetailEvent()
}