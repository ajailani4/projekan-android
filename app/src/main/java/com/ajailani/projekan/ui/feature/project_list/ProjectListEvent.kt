package com.ajailani.projekan.ui.feature.project_list

sealed class ProjectListEvent {
    object GetPagingProjects : ProjectListEvent()
    data class OnPullRefresh(val isRefreshing: Boolean) : ProjectListEvent()
}