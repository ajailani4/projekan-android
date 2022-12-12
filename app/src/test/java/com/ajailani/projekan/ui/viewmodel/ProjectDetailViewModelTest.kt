package com.ajailani.projekan.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.ajailani.projekan.data.Resource
import com.ajailani.projekan.domain.model.Project
import com.ajailani.projekan.domain.use_case.project.DeleteProjectUseCase
import com.ajailani.projekan.domain.use_case.project.GetProjectDetailUseCase
import com.ajailani.projekan.domain.use_case.task.AddTaskUseCase
import com.ajailani.projekan.domain.use_case.task.DeleteTaskUseCase
import com.ajailani.projekan.domain.use_case.task.EditTaskUseCase
import com.ajailani.projekan.ui.common.UIState
import com.ajailani.projekan.ui.feature.project_detail.ProjectDetailEvent
import com.ajailani.projekan.ui.feature.project_detail.ProjectDetailViewModel
import com.ajailani.projekan.util.TestCoroutineRule
import com.ajailani.projekan.util.project
import com.ajailani.projekan.util.task
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
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ProjectDetailViewModelTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var getProjectDetailUseCase: GetProjectDetailUseCase

    @Mock
    private lateinit var deleteProjectUseCase: DeleteProjectUseCase

    @Mock
    private lateinit var addTaskUseCase: AddTaskUseCase

    @Mock
    private lateinit var editTaskUseCase: EditTaskUseCase

    @Mock
    private lateinit var deleteTaskUseCase: DeleteTaskUseCase

    private lateinit var savedStateHandle: SavedStateHandle

    private lateinit var projectDetailViewModel: ProjectDetailViewModel

    private lateinit var onEvent: (ProjectDetailEvent) -> Unit

    @Before
    fun setUp() {
        savedStateHandle = SavedStateHandle().apply {
            set("projectId", "a1b2c3")
        }
        projectDetailViewModel = ProjectDetailViewModel(
            savedStateHandle,
            getProjectDetailUseCase,
            deleteProjectUseCase,
            addTaskUseCase,
            editTaskUseCase,
            deleteTaskUseCase
        )
        onEvent = projectDetailViewModel::onEvent
    }

    @Test
    fun `Get project detail should return success`() {
        testCoroutineRule.runTest {
            val resource = flowOf(Resource.Success(project))

            doReturn(resource).`when`(getProjectDetailUseCase)(anyString())

            onEvent(ProjectDetailEvent.GetProjectDetail)

            val project = when (val plantDetailState = projectDetailViewModel.projectDetailState) {
                is UIState.Success -> plantDetailState.data

                else -> null
            }

            assertNotNull(project)
            assertEquals("Project title should be 'Projekan'", "Projekan", project?.title)
            assertEquals("Project tasks size should be 2", 2, project?.tasks?.size)
        }
    }

    @Test
    fun `Get project detail should return fail`() {
        testCoroutineRule.runTest {
            val resource = flowOf(Resource.Error<Project>())

            doReturn(resource).`when`(getProjectDetailUseCase)(anyString())

            onEvent(ProjectDetailEvent.GetProjectDetail)

            val isSuccess = when (projectDetailViewModel.projectDetailState) {
                is UIState.Success -> true

                else -> false
            }

            assertEquals("Should be fail", false, isSuccess)
        }
    }

    @Test
    fun `Delete project should return success`() {
        testCoroutineRule.runTest {
            val resource = flowOf(Resource.Success(Any()))

            doReturn(resource).`when`(deleteProjectUseCase)(anyString())

            onEvent(ProjectDetailEvent.DeleteProject)

            val isSuccess = when (projectDetailViewModel.deleteProjectState) {
                is UIState.Success -> true

                else -> false
            }

            assertEquals("Should be success", true, isSuccess)
        }
    }

    @Test
    fun `Delete project should return fail`() {
        testCoroutineRule.runTest {
            val resource = flowOf(Resource.Error<Any>())

            doReturn(resource).`when`(deleteProjectUseCase)(anyString())

            onEvent(ProjectDetailEvent.DeleteProject)

            val isSuccess = when (projectDetailViewModel.deleteProjectState) {
                is UIState.Success -> true

                else -> false
            }

            assertEquals("Should be fail", false, isSuccess)
        }
    }

    @Test
    fun `Add task should return success`() {
        testCoroutineRule.runTest {
            val resource = flowOf(Resource.Success(Any()))

            doReturn(resource).`when`(addTaskUseCase)(
                projectId = anyString(),
                title = anyString()
            )

            onEvent(ProjectDetailEvent.AddTask)

            val isSuccess = when (projectDetailViewModel.addTaskState) {
                is UIState.Success -> true

                else -> false
            }

            assertEquals("Should be success", true, isSuccess)
        }
    }

    @Test
    fun `Add task should return fail`() {
        testCoroutineRule.runTest {
            val resource = flowOf(Resource.Error<Any>())

            doReturn(resource).`when`(addTaskUseCase)(
                projectId = anyString(),
                title = anyString()
            )

            onEvent(ProjectDetailEvent.AddTask)

            val isSuccess = when (projectDetailViewModel.addTaskState) {
                is UIState.Success -> true

                else -> false
            }

            assertEquals("Should be fail", false, isSuccess)
        }
    }

    @Test
    fun `Edit task should return success`() {
        testCoroutineRule.runTest {
            val resource = flowOf(Resource.Success(Any()))

            onEvent(ProjectDetailEvent.OnTaskSelected(task))

            doReturn(resource).`when`(editTaskUseCase)(
                id = anyString(),
                title = anyString(),
                status = any()
            )

            onEvent(ProjectDetailEvent.EditTask)

            val isSuccess = when (projectDetailViewModel.editTaskState) {
                is UIState.Success -> true

                else -> false
            }

            assertEquals("Should be success", true, isSuccess)
        }
    }

    @Test
    fun `Edit task should return fail`() {
        testCoroutineRule.runTest {
            val resource = flowOf(Resource.Error<Any>())

            onEvent(ProjectDetailEvent.OnTaskSelected(task))

            doReturn(resource).`when`(editTaskUseCase)(
                id = anyString(),
                title = anyString(),
                status = any()
            )

            onEvent(ProjectDetailEvent.EditTask)

            val isSuccess = when (projectDetailViewModel.editTaskState) {
                is UIState.Success -> true

                else -> false
            }

            assertEquals("Should be fail", false, isSuccess)
        }
    }

    @Test
    fun `Delete task should return success`() {
        testCoroutineRule.runTest {
            val resource = flowOf(Resource.Success(Any()))

            onEvent(ProjectDetailEvent.OnTaskSelected(task))

            doReturn(resource).`when`(deleteTaskUseCase)(anyString())

            onEvent(ProjectDetailEvent.DeleteTask)

            val isSuccess = when (projectDetailViewModel.deleteTaskState) {
                is UIState.Success -> true

                else -> false
            }

            assertEquals("Should be success", true, isSuccess)
        }
    }

    @Test
    fun `Delete task should return fail`() {
        testCoroutineRule.runTest {
            val resource = flowOf(Resource.Error<Any>())

            onEvent(ProjectDetailEvent.OnTaskSelected(task))

            doReturn(resource).`when`(deleteTaskUseCase)(anyString())

            onEvent(ProjectDetailEvent.DeleteTask)

            val isSuccess = when (projectDetailViewModel.deleteTaskState) {
                is UIState.Success -> true

                else -> false
            }

            assertEquals("Should be fail", false, isSuccess)
        }
    }
}