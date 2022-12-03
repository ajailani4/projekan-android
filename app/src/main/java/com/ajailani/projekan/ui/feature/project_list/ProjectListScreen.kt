package com.ajailani.projekan.ui.feature.project_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.ajailani.projekan.R
import com.ajailani.projekan.ui.common.component.CaptionImage
import com.ajailani.projekan.ui.common.component.VProjectCard
import com.ajailani.projekan.ui.common.component.VProjectCardShimmer
import com.ajailani.projekan.ui.theme.backgroundGrey
import com.ajailani.projekan.util.ProjectType

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProjectListScreen(
    projectListViewModel: ProjectListViewModel = hiltViewModel(),
    onNavigateUp: () -> Unit
) {
    val onEvent = projectListViewModel::onEvent
    val projectType = projectListViewModel.projectType
    val pagingProjects = projectListViewModel.pagingProjects.collectAsLazyPagingItems()
    val pullRefreshing = projectListViewModel.pullRefreshing

    val scaffoldState = rememberScaffoldState()
    val pullRefreshState = rememberPullRefreshState(
        refreshing = pullRefreshing,
        onRefresh = {
            onEvent(ProjectListEvent.OnPullRefresh(true))
            onEvent(ProjectListEvent.GetPagingProjects)
        }
    )

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                elevation = 0.dp,
                title = {
                    Text(
                        text = stringResource(
                            id = if (projectType == ProjectType.DEADLINE.toString()) {
                                R.string.this_week_deadlines
                            } else {
                                R.string.my_projects
                            }
                        )
                    )
                },
                backgroundColor = MaterialTheme.colors.surface,
                navigationIcon = {
                    IconButton(onClick = onNavigateUp) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back icon"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colors.backgroundGrey)
                .padding(innerPadding)
                .pullRefresh(pullRefreshState)
        ) {
            LazyColumn(contentPadding = PaddingValues(20.dp)) {
                items(pagingProjects) { projectItem ->
                    projectItem?.let {
                        VProjectCard(
                            projectItem = projectItem,
                            onClick = {}
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }

                // Handler pagingProjects state
                pagingProjects.apply {
                    when {
                        loadState.refresh is LoadState.Loading -> {
                            item {
                                VProjectCardShimmer()
                            }
                        }

                        loadState.append is LoadState.Loading -> {
                            item {
                                CircularProgressIndicator(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .wrapContentWidth(Alignment.CenterHorizontally)
                                )
                            }
                        }

                        loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached -> {
                            onEvent(ProjectListEvent.OnPullRefresh(false))

                            if (itemCount < 1) {
                                item {
                                    CaptionImage(
                                        modifier = Modifier.size(200.dp),
                                        image = painterResource(id = R.drawable.illustration_add_project),
                                        caption = stringResource(id = R.string.no_projects)
                                    )
                                }
                            }
                        }

                        loadState.append is LoadState.Error -> {
                            onEvent(ProjectListEvent.OnPullRefresh(false))

                            item {
                                LaunchedEffect(scaffoldState) {
                                    (loadState.append as LoadState.Error).error.localizedMessage?.let {
                                        scaffoldState.snackbarHostState.showSnackbar(it)
                                    }
                                }
                            }
                        }
                    }
                }
            }

            PullRefreshIndicator(
                modifier = Modifier.align(Alignment.TopCenter),
                refreshing = pullRefreshing,
                state = pullRefreshState,
                contentColor = MaterialTheme.colors.primary
            )
        }
    }
}