package com.ajailani.projekan.ui.feature.project_detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ajailani.projekan.data.Resource
import com.ajailani.projekan.domain.model.Project
import com.ajailani.projekan.domain.use_case.project.GetProjectDetailUseCase
import com.ajailani.projekan.ui.common.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProjectDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getProjectDetailUseCase: GetProjectDetailUseCase
) : ViewModel() {
    val projectId = savedStateHandle.get<String>("projectId")

    var projectDetailState by mutableStateOf<UIState<Project>>(UIState.Idle)
        private set

    init {
        onEvent(ProjectDetailEvent.GetProjectDetail)
    }

    fun onEvent(projectDetailEvent: ProjectDetailEvent) {
        when (projectDetailEvent) {
            ProjectDetailEvent.GetProjectDetail -> getProjectDetail()
        }
    }

    private fun getProjectDetail() {
        viewModelScope.launch {
            projectDetailState = UIState.Loading

            projectId?.let { id ->
                getProjectDetailUseCase(id).catch {
                    projectDetailState = UIState.Error(it.localizedMessage)
                }.collect {
                    projectDetailState = when (it) {
                        is Resource.Success -> UIState.Success(it.data)

                        is Resource.Error -> UIState.Fail(it.message)
                    }
                }
            }
        }
    }
}