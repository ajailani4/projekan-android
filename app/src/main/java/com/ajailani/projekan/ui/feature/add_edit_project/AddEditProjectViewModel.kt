package com.ajailani.projekan.ui.feature.add_edit_project

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ajailani.projekan.data.Resource
import com.ajailani.projekan.domain.model.Project
import com.ajailani.projekan.domain.use_case.project.AddProjectUseCase
import com.ajailani.projekan.domain.use_case.project.EditProjectUseCase
import com.ajailani.projekan.domain.use_case.project.GetProjectDetailUseCase
import com.ajailani.projekan.ui.common.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class AddEditProjectViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getProjectDetailUseCase: GetProjectDetailUseCase,
    private val addProjectUseCase: AddProjectUseCase,
    private val editProjectUseCase: EditProjectUseCase
) : ViewModel() {
    val projectId = savedStateHandle.get<String>("projectId")

    var projectDetailState by mutableStateOf<UIState<Project>>(UIState.Idle)
        private set

    var addProjectState by mutableStateOf<UIState<Nothing>>(UIState.Idle)
        private set

    var editProjectState by mutableStateOf<UIState<Nothing>>(UIState.Idle)
        private set

    var title by mutableStateOf("")
        private set

    var description by mutableStateOf("")
        private set

    var platform by mutableStateOf("")
        private set

    var category by mutableStateOf("")
        private set

    var deadline by mutableStateOf("")
        private set

    var icon by mutableStateOf<Any?>(null)
        private set

    init {
        if (projectId != null) onEvent(AddEditProjectEvent.GetProjectDetail)
    }

    fun onEvent(event: AddEditProjectEvent) {
        when (event) {
            AddEditProjectEvent.Idle -> idle()

            AddEditProjectEvent.GetProjectDetail -> getProjectDetail()

            AddEditProjectEvent.AddProject -> addProject()

            AddEditProjectEvent.EditProject -> editProject()

            is AddEditProjectEvent.OnIconChanged -> icon = event.icon

            is AddEditProjectEvent.OnTitleChanged -> title = event.title

            is AddEditProjectEvent.OnDescriptionChanged -> description = event.description

            is AddEditProjectEvent.OnPlatformChanged -> platform = event.platform

            is AddEditProjectEvent.OnCategoryChanged -> category = event.category

            is AddEditProjectEvent.OnDeadlineChanged -> deadline = event.deadline
        }
    }

    private fun idle() {
        projectDetailState = UIState.Idle
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

    private fun addProject() {
        addProjectState = UIState.Loading

        viewModelScope.launch {
            addProjectUseCase(
                title = title,
                description = description,
                platform = platform,
                category = category,
                deadline = deadline,
                icon = icon as File?
            ).catch {
                addProjectState = UIState.Error(it.localizedMessage)
            }.collect {
                addProjectState = when (it) {
                    is Resource.Success -> UIState.Success(null)

                    is Resource.Error -> UIState.Fail(it.message)
                }
            }
        }
    }

    private fun editProject() {
        editProjectState = UIState.Loading

        viewModelScope.launch {
            projectId?.let {
                editProjectUseCase(
                    id = projectId,
                    title = title,
                    description = description,
                    platform = platform,
                    category = category,
                    deadline = deadline,
                    icon = if (icon is File) icon as File else null
                ).catch {
                    editProjectState = UIState.Error(it.localizedMessage)
                }.collect {
                    editProjectState = when (it) {
                        is Resource.Success -> UIState.Success(null)

                        is Resource.Error -> UIState.Fail(it.message)
                    }
                }
            }
        }
    }
}