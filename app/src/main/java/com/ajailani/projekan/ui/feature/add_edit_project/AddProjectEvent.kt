package com.ajailani.projekan.ui.feature.add_edit_project

sealed class AddProjectEvent {
    object AddProject : AddProjectEvent()
    data class OnTitleChanged(val title: String) : AddProjectEvent()
    data class OnDescriptionChanged(val description: String) : AddProjectEvent()
    data class OnPlatformChanged(val platform: String) : AddProjectEvent()
    data class OnCategoryChanged(val category: String) : AddProjectEvent()
    data class OnDeadlineChanged(val deadline: String) : AddProjectEvent()
    data class OnIconChanged(val icon: Any) : AddProjectEvent()
}
