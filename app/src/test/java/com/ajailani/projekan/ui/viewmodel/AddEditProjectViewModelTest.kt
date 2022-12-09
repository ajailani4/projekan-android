package com.ajailani.projekan.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.ajailani.projekan.data.Resource
import com.ajailani.projekan.domain.use_case.project.AddProjectUseCase
import com.ajailani.projekan.domain.use_case.project.EditProjectUseCase
import com.ajailani.projekan.domain.use_case.project.GetProjectDetailUseCase
import com.ajailani.projekan.ui.common.UIState
import com.ajailani.projekan.ui.feature.add_edit_project.AddEditProjectEvent
import com.ajailani.projekan.ui.feature.add_edit_project.AddEditProjectViewModel
import com.ajailani.projekan.util.TestCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.isNull

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class AddEditProjectViewModelTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var getProjectDetailUseCase: GetProjectDetailUseCase

    @Mock
    private lateinit var addProjectUseCase: AddProjectUseCase

    @Mock
    private lateinit var editProjectUseCase: EditProjectUseCase

    private lateinit var savedStateHandle: SavedStateHandle

    private lateinit var addEditProjectViewModel: AddEditProjectViewModel

    private lateinit var onEvent: (AddEditProjectEvent) -> Unit

    @Before
    fun setUp() {
        savedStateHandle = SavedStateHandle().apply {
            set("projectId", "a1b2c3")
        }
        addEditProjectViewModel = AddEditProjectViewModel(
            savedStateHandle,
            getProjectDetailUseCase,
            addProjectUseCase,
            editProjectUseCase
        )
        onEvent = addEditProjectViewModel::onEvent
    }

    @Test
    fun `Add project should return success`() {
        testCoroutineRule.runTest {
            val resource = flowOf(Resource.Success(Any()))

            doReturn(resource).`when`(addProjectUseCase)(
                title = anyString(),
                description = anyString(),
                platform = anyString(),
                category = anyString(),
                deadline = anyString(),
                icon = isNull()
            )

            onEvent(AddEditProjectEvent.AddProject)

            val isSuccess = when (addEditProjectViewModel.addProjectState) {
                is UIState.Success -> true

                else -> false
            }

            assertEquals("Should return success", true, isSuccess)
        }
    }

    @Test
    fun `Add project should return fail`() {
        testCoroutineRule.runTest {
            val resource = flowOf(Resource.Error<Any>())

            doReturn(resource).`when`(addProjectUseCase)(
                title = anyString(),
                description = anyString(),
                platform = anyString(),
                category = anyString(),
                deadline = anyString(),
                icon = isNull()
            )

            onEvent(AddEditProjectEvent.AddProject)

            val isSuccess = when (addEditProjectViewModel.addProjectState) {
                is UIState.Success -> true

                else -> false
            }

            assertEquals("Should return fail", false, isSuccess)
        }
    }

    @Test
    fun `Edit project should return success`() {
        testCoroutineRule.runTest {
            val resource = flowOf(Resource.Success(Any()))

            doReturn(resource).`when`(editProjectUseCase)(
                id = anyString(),
                title = anyString(),
                description = anyString(),
                platform = anyString(),
                category = anyString(),
                deadline = anyString(),
                icon = isNull()
            )

            onEvent(AddEditProjectEvent.EditProject)

            val isSuccess = when (addEditProjectViewModel.editProjectState) {
                is UIState.Success -> true

                else -> false
            }

            assertEquals("Should return success", true, isSuccess)
        }
    }

    @Test
    fun `Edit project should return fail`() {
        testCoroutineRule.runTest {
            val resource = flowOf(Resource.Error<Any>())

            doReturn(resource).`when`(editProjectUseCase)(
                id = anyString(),
                title = anyString(),
                description = anyString(),
                platform = anyString(),
                category = anyString(),
                deadline = anyString(),
                icon = isNull()
            )

            onEvent(AddEditProjectEvent.EditProject)

            val isSuccess = when (addEditProjectViewModel.editProjectState) {
                is UIState.Success -> true

                else -> false
            }

            assertEquals("Should return fail", false, isSuccess)
        }
    }
}