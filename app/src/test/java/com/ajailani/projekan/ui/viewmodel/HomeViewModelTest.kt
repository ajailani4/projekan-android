package com.ajailani.projekan.ui.viewmodel

import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import com.ajailani.projekan.data.Resource
import com.ajailani.projekan.domain.model.ProjectItem
import com.ajailani.projekan.domain.model.UserProfile
import com.ajailani.projekan.domain.use_case.project.GetPagingProjectsUseCase
import com.ajailani.projekan.domain.use_case.project.GetProjectsUseCase
import com.ajailani.projekan.domain.use_case.user_profile.GetUserProfileUseCase
import com.ajailani.projekan.ui.common.UIState
import com.ajailani.projekan.ui.feature.home.HomeEvent
import com.ajailani.projekan.ui.feature.home.HomeViewModel
import com.ajailani.projekan.util.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.isNull

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var getUserProfileUseCase: GetUserProfileUseCase

    @Mock
    private lateinit var getProjectsUseCase: GetProjectsUseCase

    @Mock
    private lateinit var getPagingProjectsUseCase: GetPagingProjectsUseCase

    private lateinit var homeViewModel: HomeViewModel

    private lateinit var onEvent: (HomeEvent) -> Unit

    @Before
    fun setUp() {
        homeViewModel = HomeViewModel(
            getUserProfileUseCase,
            getProjectsUseCase,
            getPagingProjectsUseCase
        )
        onEvent = homeViewModel::onEvent
    }

    @Test
    fun `Get user profile should return success`() {
        testCoroutineRule.runTest {
            val resource = flowOf(Resource.Success(userProfile))

            doReturn(resource).`when`(getUserProfileUseCase)()

            onEvent(HomeEvent.GetUserProfile)

            val userProfile = when (val userProfileState = homeViewModel.userProfileState) {
                is UIState.Success -> userProfileState.data

                else -> null
            }

            assertNotNull(userProfile)
            assertEquals("Name should be 'George'", "George", userProfile?.name)
        }
    }

    @Test
    fun `Get user profile should return fail`() {
        testCoroutineRule.runTest {
            val resource = flowOf(Resource.Error<UserProfile>())

            doReturn(resource).`when`(getUserProfileUseCase)()

            onEvent(HomeEvent.GetUserProfile)

            val isSuccess = when (homeViewModel.userProfileState) {
                is UIState.Success -> true

                else -> false
            }

            assertEquals("Should be fail", false, isSuccess)
        }
    }

    @Test
    fun `Get deadlines should return success`() {
        testCoroutineRule.runTest {
            val resource = flowOf(Resource.Success(projects))

            doReturn(resource).`when`(getProjectsUseCase)(
                page = anyInt(),
                size = anyInt(),
                type = any()
            )

            onEvent(HomeEvent.GetDeadlines)

            val deadlines = when (val deadlinesState = homeViewModel.deadlinesState) {
                is UIState.Success -> deadlinesState.data

                else -> null
            }

            assertNotNull(deadlines)
            assertEquals("Deadlines size should be 2", 2, deadlines?.size)
        }
    }

    @Test
    fun `Get deadlines should return fail`() {
        testCoroutineRule.runTest {
            val resource = flowOf(Resource.Error<List<ProjectItem>>())

            doReturn(resource).`when`(getProjectsUseCase)(
                page = anyInt(),
                size = anyInt(),
                type = any()
            )

            onEvent(HomeEvent.GetDeadlines)

            val isSuccess = when (homeViewModel.deadlinesState) {
                is UIState.Success -> true

                else -> false
            }

            assertEquals("Should be fail", false, isSuccess)
        }
    }

    @Test
    fun `Get projects should return success`() {
        testCoroutineRule.runTest {
            val pagingData = flowOf(PagingData.from(projects))

            doReturn(pagingData).`when`(getPagingProjectsUseCase)(type = isNull())

            onEvent(HomeEvent.GetProjects)

            val pagingProjects = homeViewModel.pagingProjects.value
            val differ = AsyncPagingDataDiffer(
                diffCallback = DiffCallback(),
                updateCallback = ListCallback(),
                workerDispatcher = Dispatchers.Main
            )

            differ.submitData(pagingProjects)

            assertEquals("Should be success", projects, differ.snapshot().items)
        }
    }
}