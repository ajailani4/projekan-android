package com.ajailani.projekan.ui.feature.add_edit_project

sealed class AddEditProjectEvent {
    object Idle : AddEditProjectEvent()
    object AddProject : AddEditProjectEvent()
    object GetProjectDetail : AddEditProjectEvent()
    data class OnIconChanged(val icon: Any) : AddEditProjectEvent()
    data class OnTitleChanged(val title: String) : AddEditProjectEvent()
    data class OnDescriptionChanged(val description: String) : AddEditProjectEvent()
    data class OnPlatformChanged(val platform: String) : AddEditProjectEvent()
    data class OnCategoryChanged(val category: String) : AddEditProjectEvent()
    data class OnDeadlineChanged(val deadline: String) : AddEditProjectEvent()
}
