package com.ajailani.projekan.ui.feature.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ajailani.projekan.data.Resource
import com.ajailani.projekan.domain.model.Project
import com.ajailani.projekan.domain.model.UserProfile
import com.ajailani.projekan.domain.use_case.project.GetPagingProjectsUseCase
import com.ajailani.projekan.domain.use_case.project.GetProjectsUseCase
import com.ajailani.projekan.domain.use_case.user_profile.GetUserProfileUseCase
import com.ajailani.projekan.ui.common.UIState
import com.ajailani.projekan.util.ProjectType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val getProjectsUseCase: GetProjectsUseCase,
    private val getPagingProjectsUseCase: GetPagingProjectsUseCase
) : ViewModel() {
    var userProfileState by mutableStateOf<UIState<UserProfile>>(UIState.Idle)
        private set

    var deadlinesState by mutableStateOf<UIState<List<Project>>>(UIState.Idle)
        private set

    private var _pagingProjects = MutableStateFlow<PagingData<Project>>(PagingData.empty())
    val pagingProjects: StateFlow<PagingData<Project>> = _pagingProjects

    var pullRefreshing by mutableStateOf(false)
        private set

    init {
        onEvent(HomeEvent.GetUserProfile)
        onEvent(HomeEvent.GetDeadlines)
        onEvent(HomeEvent.GetProjects)
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.GetUserProfile -> getUserProfile()

            HomeEvent.GetDeadlines -> getDeadlines()

            HomeEvent.GetProjects -> getProjects()

            is HomeEvent.OnPullRefresh -> pullRefreshing = event.isRefreshing
        }
    }

    private fun getUserProfile() {
        userProfileState = UIState.Loading

        viewModelScope.launch {
            getUserProfileUseCase().catch {
                userProfileState = UIState.Error(it.localizedMessage)
            }.collect {
                userProfileState = when (it) {
                    is Resource.Success -> UIState.Success(it.data)

                    is Resource.Error -> UIState.Fail(it.message)
                }
            }
        }
    }

    private fun getDeadlines() {
        deadlinesState = UIState.Loading

        viewModelScope.launch {
            getProjectsUseCase(
                page = 1,
                size = 7,
                type = ProjectType.DEADLINE
            ).catch {
                deadlinesState = UIState.Error(it.localizedMessage)
            }.collect {
                deadlinesState = when (it) {
                    is Resource.Success -> UIState.Success(it.data)

                    is Resource.Error -> UIState.Fail(it.message)
                }
            }
        }
    }

    private fun getProjects() {
        viewModelScope.launch {
            getPagingProjectsUseCase()
                .cachedIn(viewModelScope)
                .collect {
                    _pagingProjects.value = it
                }
        }
    }
}