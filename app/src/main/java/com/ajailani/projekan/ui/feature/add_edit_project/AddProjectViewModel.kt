package com.ajailani.projekan.ui.feature.add_edit_project

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ajailani.projekan.data.Resource
import com.ajailani.projekan.domain.use_case.project.AddProjectUseCase
import com.ajailani.projekan.ui.common.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class AddProjectViewModel @Inject constructor(
    private val addProjectUseCase: AddProjectUseCase
) : ViewModel() {
    var addProjectState by mutableStateOf<UIState<Nothing>>(UIState.Idle)
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

    fun onEvent(event: AddProjectEvent) {
        when (event) {
            AddProjectEvent.AddProject -> addProject()

            is AddProjectEvent.OnTitleChanged -> title = event.title

            is AddProjectEvent.OnDescriptionChanged -> description = event.description

            is AddProjectEvent.OnPlatformChanged -> platform = event.platform

            is AddProjectEvent.OnCategoryChanged -> category = event.category

            is AddProjectEvent.OnDeadlineChanged -> deadline = event.deadline

            is AddProjectEvent.OnIconChanged -> icon = event.icon
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
}