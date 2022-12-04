package com.ajailani.projekan.ui.feature.project_detail

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ajailani.projekan.R
import com.ajailani.projekan.domain.model.TaskItem
import com.ajailani.projekan.ui.common.UIState
import com.ajailani.projekan.ui.common.component.Label
import com.ajailani.projekan.ui.feature.project_detail.component.TaskItemCard
import com.ajailani.projekan.ui.theme.*
import com.ajailani.projekan.util.Formatter
import com.ajailani.projekan.util.ProjectStatus

@Composable
fun ProjectDetailScreen(
    projectDetailViewModel: ProjectDetailViewModel = hiltViewModel(),
    onNavigateUp: () -> Unit
) {
    val projectDetailState = projectDetailViewModel.projectDetailState

    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                elevation = 0.dp,
                title = {
                    Text(
                        text = stringResource(id = R.string.project_detail)
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
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "Project more option icon"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add task icon"
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colors.backgroundGrey)
                .padding(innerPadding)
        ) {
            LazyColumn {
                when (projectDetailState) {
                    UIState.Loading -> {
                        // Shimmer
                    }

                    is UIState.Success -> {
                        projectDetailState.data?.let { project ->
                            item {
                                Column(
                                    modifier = Modifier
                                        .background(color = MaterialTheme.colors.surface)
                                        .padding(20.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            AsyncImage(
                                                modifier = Modifier
                                                    .size(60.dp)
                                                    .clip(MaterialTheme.shapes.medium),
                                                model = ImageRequest.Builder(LocalContext.current)
                                                    .data(project.icon)
                                                    .build(),
                                                placeholder = painterResource(id = R.drawable.ic_default_project),
                                                contentScale = ContentScale.Crop,
                                                contentDescription = "Project icon"
                                            )
                                            Spacer(modifier = Modifier.width(20.dp))
                                            Text(
                                                text = project.title,
                                                color = MaterialTheme.colors.onSurface,
                                                style = MaterialTheme.typography.h3
                                            )
                                        }
                                        Label(
                                            title = stringResource(
                                                id = when (project.status) {
                                                    ProjectStatus.TODO -> R.string.todo
                                                    ProjectStatus.IN_PROGRESS -> R.string.in_progress
                                                    ProjectStatus.DONE -> R.string.done
                                                }
                                            ),
                                            backgroundColor = when (project.status) {
                                                ProjectStatus.TODO -> Blue
                                                ProjectStatus.IN_PROGRESS -> Yellow
                                                ProjectStatus.DONE -> MaterialTheme.colors.secondary
                                            },
                                            textColor = Color.White
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(20.dp))
                                    Text(
                                        text = project.description,
                                        color = MaterialTheme.colors.onSurface
                                    )
                                    Spacer(modifier = Modifier.height(20.dp))
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Column {
                                            Row {
                                                Label(
                                                    title = project.platform,
                                                    backgroundColor = MaterialTheme.colors.secondary,
                                                    textColor = MaterialTheme.colors.secondaryVariant
                                                )
                                                Spacer(modifier = Modifier.width(10.dp))
                                                Label(
                                                    title = project.category,
                                                    backgroundColor = MaterialTheme.colors.primary,
                                                    textColor = MaterialTheme.colors.primaryVariant
                                                )
                                            }
                                            Spacer(modifier = Modifier.height(15.dp))
                                            Text(
                                                text = buildAnnotatedString {
                                                    withStyle(
                                                        SpanStyle(
                                                            color = Grey
                                                        )
                                                    ) {
                                                        append(stringResource(id = R.string.deadline))
                                                        append(": ")
                                                    }

                                                    withStyle(
                                                        SpanStyle(
                                                            color = MaterialTheme.colors.onSurface
                                                        )
                                                    ) {
                                                        append(Formatter.formatDate(project.deadline))
                                                    }
                                                },
                                                style = MaterialTheme.typography.body1.copy(
                                                    fontWeight = FontWeight.Medium
                                                )
                                            )
                                        }
                                        Box(contentAlignment = Alignment.Center) {
                                            Text(
                                                text = "${project.progress}%",
                                                color = MaterialTheme.colors.primary,
                                                fontWeight = FontWeight.SemiBold,
                                                style = MaterialTheme.typography.body1
                                            )
                                            Canvas(modifier = Modifier.size(60.dp)) {
                                                drawCircle(
                                                    color = LightGrey,
                                                    radius = size.minDimension / 2.0f - (7.dp.toPx() / 2),
                                                    style = Stroke(width = 7.dp.toPx())
                                                )
                                            }
                                            CircularProgressIndicator(
                                                modifier = Modifier.size(60.dp),
                                                progress = project.progress.toFloat() / 100f,
                                                strokeWidth = 7.dp
                                            )
                                        }
                                    }
                                }
                            }

                            item {
                                Spacer(modifier = Modifier.height(25.dp))
                            }

                            project.tasks?.let { tasksSection(it) }
                        }
                    }

                    is UIState.Fail -> {
                        item {
                            LaunchedEffect(scaffoldState) {
                                projectDetailState.message?.let {
                                    scaffoldState.snackbarHostState.showSnackbar(it)
                                }
                            }
                        }
                    }

                    is UIState.Error -> {
                        item {
                            LaunchedEffect(scaffoldState) {
                                projectDetailState.message?.let {
                                    scaffoldState.snackbarHostState.showSnackbar(it)
                                }
                            }
                        }
                    }

                    else -> {}
                }
            }
        }
    }
}

private fun LazyListScope.tasksSection(tasks: List<TaskItem>) {
    item {
        Text(
            modifier = Modifier.padding(horizontal = 20.dp),
            text = stringResource(id = R.string.tasks),
            style = MaterialTheme.typography.h3
        )
    }

    item {
        Spacer(modifier = Modifier.height(15.dp))
    }

    items(tasks) { taskItem ->
        TaskItemCard(
            modifier = Modifier.padding(horizontal = 20.dp),
            taskItem = taskItem,
            onChecked = {},
            onMoreClicked = {}
        )
        Spacer(modifier = Modifier.height(15.dp))
    }
}