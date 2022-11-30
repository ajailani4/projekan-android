package com.ajailani.projekan.ui.feature.home

sealed class HomeEvent {
    object GetUserProfile : HomeEvent()
    object GetDeadlines : HomeEvent()
    object GetProjects : HomeEvent()
    data class OnPullRefresh(val isRefreshing: Boolean) : HomeEvent()
}
