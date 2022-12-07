package com.ajailani.projekan.ui.feature.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.ajailani.projekan.R
import com.ajailani.projekan.domain.model.ProjectItem
import com.ajailani.projekan.domain.model.UserProfile
import com.ajailani.projekan.ui.common.SharedViewModel
import com.ajailani.projekan.ui.common.UIState
import com.ajailani.projekan.ui.common.component.CaptionImage
import com.ajailani.projekan.ui.common.component.VProjectItemCard
import com.ajailani.projekan.ui.common.component.VProjectItemCardShimmer
import com.ajailani.projekan.ui.feature.home.component.HProjectItemCard
import com.ajailani.projekan.ui.feature.home.component.HProjectItemCardShimmer
import com.ajailani.projekan.ui.feature.home.component.HomeHeaderShimmer
import com.ajailani.projekan.ui.theme.backgroundGrey
import com.ajailani.projekan.util.ProjectType

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel(),
    sharedViewModel: SharedViewModel,
    onNavigateToProjectList: (ProjectType) -> Unit,
    onNavigateToProjectDetail: (String) -> Unit,
    onNavigateToAddEditProject: () -> Unit
) {
    val onEvent = homeViewModel::onEvent
    val userProfileState = homeViewModel.userProfileState
    val deadlinesState = homeViewModel.deadlinesState
    val pagingProjects = homeViewModel.pagingProjects.collectAsLazyPagingItems()
    val pullRefreshing = homeViewModel.pullRefreshing
    val lazyListState = if (pagingProjects.itemCount > 0) homeViewModel.lazyListState else rememberLazyListState()

    val reloaded = sharedViewModel.reloaded
    val onReloadedChanged = sharedViewModel::onReloadedChanged

    val scaffoldState = rememberScaffoldState()
    val pullRefreshState = rememberPullRefreshState(
        refreshing = pullRefreshing,
        onRefresh = {
            onEvent(HomeEvent.OnPullRefresh(true))
            onEvent(HomeEvent.GetUserProfile)
            onEvent(HomeEvent.GetDeadlines)
            onEvent(HomeEvent.GetProjects)
        }
    )

    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateToAddEditProject) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add project icon"
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.backgroundGrey)
                .padding(innerPadding)
                .pullRefresh(pullRefreshState)
        ) {
            LazyColumn(state = lazyListState) {
                item {
                    Column(
                        modifier = Modifier
                            .background(color = MaterialTheme.colors.primary)
                            .padding(vertical = 20.dp)
                    ) {
                        Header(
                            onEvent = onEvent,
                            userProfileState = userProfileState,
                            onReloadedChanged = onReloadedChanged,
                            scaffoldState = scaffoldState
                        )
                        Spacer(modifier = Modifier.height(35.dp))
                        ThisWeekDeadlinesSection(
                            onEvent = onEvent,
                            deadlinesState = deadlinesState,
                            onReloadedChanged = onReloadedChanged,
                            onViewAllClicked = { onNavigateToProjectList(ProjectType.DEADLINE) },
                            onNavigateToProjectDetail = onNavigateToProjectDetail,
                            scaffoldState = scaffoldState
                        )
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(25.dp))
                }

                myProjectsSection(
                    onEvent = onEvent,
                    pagingProjects = pagingProjects,
                    onReloadedChanged = onReloadedChanged,
                    onNavigateToProjectDetail = onNavigateToProjectDetail,
                    scaffoldState = scaffoldState
                )
            }

            PullRefreshIndicator(
                modifier = Modifier.align(Alignment.TopCenter),
                refreshing = pullRefreshing,
                state = pullRefreshState,
                contentColor = MaterialTheme.colors.primary
            )
        }

        // Observe reloaded state from SharedViewModel
        if (reloaded) {
            onEvent(HomeEvent.GetUserProfile)
            onEvent(HomeEvent.GetDeadlines)
            onEvent(HomeEvent.GetProjects)
        }
    }
}

@Composable
private fun Header(
    onEvent: (HomeEvent) -> Unit,
    userProfileState: UIState<UserProfile>,
    onReloadedChanged: (Boolean) -> Unit,
    scaffoldState: ScaffoldState
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        when (userProfileState) {
            UIState.Loading -> {
                HomeHeaderShimmer()
            }

            is UIState.Success -> {
                onEvent(HomeEvent.OnPullRefresh(false))
                onReloadedChanged(false)

                val userProfile = userProfileState.data

                Column {
                    userProfile?.let {
                        Text(
                            text = "${stringResource(id = R.string.hello)}, " +
                                    userProfile.name.split(" ")[0],
                            color = MaterialTheme.colors.onPrimary,
                            style = MaterialTheme.typography.subtitle1.copy(
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = stringResource(id = R.string.manage_your_projects),
                        color = MaterialTheme.colors.onPrimary,
                    )
                }
                Image(
                    modifier = Modifier
                        .size(55.dp)
                        .clip(CircleShape),
                    painter = if (userProfile?.avatar != null) {
                        rememberAsyncImagePainter(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(userProfile.avatar)
                                .build()
                        )
                    } else {
                        painterResource(id = R.drawable.img_default_ava)
                    },
                    contentDescription = "User avatar"
                )
            }

            is UIState.Fail -> {
                onEvent(HomeEvent.OnPullRefresh(false))

                LaunchedEffect(scaffoldState) {
                    userProfileState.message?.let {
                        scaffoldState.snackbarHostState.showSnackbar(it)
                    }
                }
            }

            is UIState.Error -> {
                onEvent(HomeEvent.OnPullRefresh(false))

                LaunchedEffect(scaffoldState) {
                    userProfileState.message?.let {
                        scaffoldState.snackbarHostState.showSnackbar(it)
                    }
                }
            }

            else -> {}
        }
    }
}

@Composable
private fun ThisWeekDeadlinesSection(
    onEvent: (HomeEvent) -> Unit,
    deadlinesState: UIState<List<ProjectItem>>,
    onReloadedChanged: (Boolean) -> Unit,
    onViewAllClicked: () -> Unit,
    onNavigateToProjectDetail: (String) -> Unit,
    scaffoldState: ScaffoldState
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.this_week_deadlines),
                color = MaterialTheme.colors.onPrimary,
                style = MaterialTheme.typography.h3
            )
            ClickableText(
                text = buildAnnotatedString {
                    withStyle(
                        SpanStyle(
                            color = MaterialTheme.colors.onPrimary
                        )
                    ) {
                        append(stringResource(id = R.string.view_all))
                    }

                },
                style = MaterialTheme.typography.body2,
                onClick = { onViewAllClicked() }
            )
        }
        Spacer(modifier = Modifier.height(15.dp))

        when (deadlinesState) {
            UIState.Loading -> {
                HProjectItemCardShimmer()
            }

            is UIState.Success -> {
                onEvent(HomeEvent.OnPullRefresh(false))
                onReloadedChanged(false)

                deadlinesState.data?.let { projects ->
                    if (projects.isNotEmpty()) {
                        LazyRow(contentPadding = PaddingValues(horizontal = 20.dp)) {
                            items(projects) { projectItem ->
                                HProjectItemCard(
                                    projectItem = projectItem,
                                    onClick = { onNavigateToProjectDetail(projectItem.id) }
                                )

                                if (projectItem != projects.last()) {
                                    Spacer(modifier = Modifier.width(15.dp))
                                }
                            }
                        }
                    } else {
                        CaptionImage(
                            modifier = Modifier.size(140.dp),
                            image = painterResource(id = R.drawable.illustration_no_data),
                            caption = stringResource(id = R.string.no_deadlines),
                            captionColor = MaterialTheme.colors.onPrimary
                        )
                    }
                }
            }

            is UIState.Fail -> {
                onEvent(HomeEvent.OnPullRefresh(false))

                LaunchedEffect(scaffoldState) {
                    deadlinesState.message?.let {
                        scaffoldState.snackbarHostState.showSnackbar(it)
                    }
                }
            }

            is UIState.Error -> {
                onEvent(HomeEvent.OnPullRefresh(false))

                LaunchedEffect(scaffoldState) {
                    deadlinesState.message?.let {
                        scaffoldState.snackbarHostState.showSnackbar(it)
                    }
                }
            }

            else -> {}
        }
    }
}

private fun LazyListScope.myProjectsSection(
    onEvent: (HomeEvent) -> Unit,
    pagingProjects: LazyPagingItems<ProjectItem>,
    onReloadedChanged: (Boolean) -> Unit,
    onNavigateToProjectDetail: (String) -> Unit,
    scaffoldState: ScaffoldState
) {
    item {
        Text(
            modifier = Modifier.padding(horizontal = 20.dp),
            text = stringResource(id = R.string.my_projects),
            style = MaterialTheme.typography.h3
        )
    }

    item {
        Spacer(modifier = Modifier.height(15.dp))
    }

    items(pagingProjects) { projectItem ->
        projectItem?.let {
            VProjectItemCard(
                modifier = Modifier.padding(horizontal = 20.dp),
                projectItem = projectItem,
                onClick = { onNavigateToProjectDetail(projectItem.id) }
            )
            Spacer(modifier = Modifier.height(20.dp))
        }
    }

    // Handle pagingProjects state
    pagingProjects.apply {
        when {
            loadState.refresh is LoadState.Loading -> {
                item {
                    VProjectItemCardShimmer(modifier = Modifier.padding(horizontal = 20.dp))
                }
            }

            loadState.append is LoadState.Loading -> {
                item {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(CenterHorizontally)
                    )
                }
            }

            loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached -> {
                onEvent(HomeEvent.OnPullRefresh(false))
                onReloadedChanged(false)

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
                onEvent(HomeEvent.OnPullRefresh(false))

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