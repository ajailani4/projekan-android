package com.ajailani.projekan.ui.feature.project_list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ajailani.projekan.domain.model.Project
import com.ajailani.projekan.domain.use_case.project.GetPagingProjectsUseCase
import com.ajailani.projekan.domain.use_case.project.GetProjectsUseCase
import com.ajailani.projekan.util.ProjectType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProjectListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getPagingProjectsUseCase: GetPagingProjectsUseCase
) : ViewModel() {
    val projectType = savedStateHandle.get<String>("projectType")

    private var _pagingProjects = MutableStateFlow<PagingData<Project>>(PagingData.empty())
    val pagingProjects = _pagingProjects.asStateFlow()

    var pullRefreshing by mutableStateOf(false)
        private set

    init {
        getPagingProjects()
    }

    fun onEvent(event: ProjectListEvent) {
        when (event) {
            ProjectListEvent.GetPagingProjects -> getPagingProjects()

            is ProjectListEvent.OnPullRefresh -> pullRefreshing = event.isRefreshing
        }
    }

    private fun getPagingProjects() {
        viewModelScope.launch {
            getPagingProjectsUseCase(
                if (projectType != null) ProjectType.valueOf(projectType) else null
            ).cachedIn(viewModelScope).collect {
                _pagingProjects.value = it
            }
        }
    }
}