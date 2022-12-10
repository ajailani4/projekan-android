package com.ajailani.projekan.ui.feature.project_detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ajailani.projekan.data.Resource
import com.ajailani.projekan.domain.model.Project
import com.ajailani.projekan.domain.use_case.project.DeleteProjectUseCase
import com.ajailani.projekan.domain.use_case.project.GetProjectDetailUseCase
import com.ajailani.projekan.domain.use_case.task.AddTaskUseCase
import com.ajailani.projekan.ui.common.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProjectDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getProjectDetailUseCase: GetProjectDetailUseCase,
    private val deleteProjectUseCase: DeleteProjectUseCase,
    private val addTaskUseCase: AddTaskUseCase
) : ViewModel() {
    val projectId = savedStateHandle.get<String>("projectId")

    var projectDetailState by mutableStateOf<UIState<Project>>(UIState.Idle)
        private set

    var deleteProjectState by mutableStateOf<UIState<Nothing>>(UIState.Idle)
        private set

    var addTaskState by mutableStateOf<UIState<Nothing>>(UIState.Idle)
        private set

    var taskTitle by mutableStateOf("")
        private set

    var pullRefreshing by mutableStateOf(false)
        private set

    // 1: Project more menu, 2: Task more menu
    var moreMenu by mutableStateOf(0)
        private set

    var addEditTaskSheetVis by mutableStateOf(false)
        private set

    var deleteProjectDialogVis by mutableStateOf(false)
        private set

    init {
        onEvent(ProjectDetailEvent.GetProjectDetail)
    }

    fun onEvent(event: ProjectDetailEvent) {
        when (event) {
            ProjectDetailEvent.GetProjectDetail -> getProjectDetail()

            ProjectDetailEvent.DeleteProject -> deleteProject()

            ProjectDetailEvent.AddTask -> addTask()

            is ProjectDetailEvent.OnTaskTitleChanged -> taskTitle = event.taskTitle

            is ProjectDetailEvent.OnPullRefresh -> pullRefreshing = event.isRefreshing

            is ProjectDetailEvent.OnMoreMenuClicked -> moreMenu = event.actionMenu

            is ProjectDetailEvent.OnAddEditTaskSheetVisChanged -> addEditTaskSheetVis = event.isVisible

            is ProjectDetailEvent.OnDeleteProjectDialogVisChanged -> deleteProjectDialogVis = event.isVisible
        }
    }

    private fun getProjectDetail() {
        projectDetailState = UIState.Loading

        viewModelScope.launch {
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

    private fun deleteProject() {
        deleteProjectState = UIState.Loading

        viewModelScope.launch {
            projectId?.let { id ->
                deleteProjectUseCase(id).catch {
                    deleteProjectState = UIState.Error(it.localizedMessage)
                }.collect {
                    deleteProjectState = when (it) {
                        is Resource.Success -> UIState.Success(null)

                        is Resource.Error -> UIState.Fail(it.message)
                    }
                }
            }
        }
    }

    private fun addTask() {
        addTaskState = UIState.Loading

        viewModelScope.launch {
            projectId?.let { id ->
                addTaskUseCase(
                    projectId = id,
                    title = taskTitle
                ).catch {
                    addTaskState = UIState.Error(it.localizedMessage)
                }.collect {
                    addTaskState = when (it) {
                        is Resource.Success -> UIState.Success(null)

                        is Resource.Error -> UIState.Fail(it.message)
                    }
                }
            }
        }
    }
}