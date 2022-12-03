package com.ajailani.projekan.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import com.ajailani.projekan.domain.use_case.project.GetPagingProjectsUseCase
import com.ajailani.projekan.ui.feature.project_list.ProjectListEvent
import com.ajailani.projekan.ui.feature.project_list.ProjectListViewModel
import com.ajailani.projekan.util.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ProjectListViewModelTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var getPagingProjectsUseCase: GetPagingProjectsUseCase

    private lateinit var savedStateHandle: SavedStateHandle

    private lateinit var projectListViewModel: ProjectListViewModel

    private lateinit var onEvent: (ProjectListEvent) -> Unit

    @Before
    fun setUp() {
        savedStateHandle = SavedStateHandle().apply {
            set("projectType", ProjectType.DEADLINE.toString())
        }
        projectListViewModel = ProjectListViewModel(savedStateHandle, getPagingProjectsUseCase)
        onEvent = projectListViewModel::onEvent
    }

    @Test
    fun `Get projects should return success`() {
        testCoroutineRule.runTest {
            val pagingData = flowOf(PagingData.from(projectItems))

            doReturn(pagingData).`when`(getPagingProjectsUseCase)(type = any())

            onEvent(ProjectListEvent.GetPagingProjects)

            val pagingProjects = projectListViewModel.pagingProjects.value
            val differ = AsyncPagingDataDiffer(
                diffCallback = DiffCallback(),
                updateCallback = ListCallback(),
                workerDispatcher = Dispatchers.Main
            )

            differ.submitData(pagingProjects)

            assertEquals("Should be success", projectItems, differ.snapshot().items)
        }
    }
}