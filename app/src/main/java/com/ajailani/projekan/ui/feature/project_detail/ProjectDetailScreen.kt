package com.ajailani.projekan.ui.feature.project_detail

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ajailani.projekan.R
import com.ajailani.projekan.domain.model.TaskItem
import com.ajailani.projekan.ui.common.CustomAlertDialog
import com.ajailani.projekan.ui.common.SharedViewModel
import com.ajailani.projekan.ui.common.UIState
import com.ajailani.projekan.ui.common.component.CaptionImage
import com.ajailani.projekan.ui.common.component.Label
import com.ajailani.projekan.ui.common.component.ProgressBarWithBackground
import com.ajailani.projekan.ui.feature.project_detail.component.*
import com.ajailani.projekan.ui.theme.*
import com.ajailani.projekan.util.Formatter
import com.ajailani.projekan.util.ProjectStatus
import com.ajailani.projekan.util.TaskStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProjectDetailScreen(
    projectDetailViewModel: ProjectDetailViewModel = hiltViewModel(),
    sharedViewModel: SharedViewModel,
    onNavigateUp: () -> Unit,
    onNavigateToAddEditProject: (String) -> Unit
) {
    val onEvent = projectDetailViewModel::onEvent
    val projectId = projectDetailViewModel.projectId
    val projectDetailState = projectDetailViewModel.projectDetailState
    val deleteProjectState = projectDetailViewModel.deleteProjectState
    val addTaskState = projectDetailViewModel.addTaskState
    val editTaskState = projectDetailViewModel.editTaskState
    val taskTitle = projectDetailViewModel.taskTitle
    val selectedTask = projectDetailViewModel.selectedTask
    val pullRefreshing = projectDetailViewModel.pullRefreshing
    val moreMenu = projectDetailViewModel.moreMenu
    val addEditTaskSheetVis = projectDetailViewModel.addEditTaskSheetVis
    val deleteProjectDialogVis = projectDetailViewModel.deleteProjectDialogVis
    val deleteTaskDialogVis = projectDetailViewModel.deleteTaskDialogVis
    val tasks = projectDetailViewModel.tasks

    val reloaded = sharedViewModel.reloaded
    val onReloadedChanged = sharedViewModel::onReloadedChanged

    val modalBottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    val pullRefreshState = rememberPullRefreshState(
        refreshing = pullRefreshing,
        onRefresh = {
            onEvent(ProjectDetailEvent.OnPullRefresh(true))
            onEvent(ProjectDetailEvent.GetProjectDetail)
        }
    )

    ModalBottomSheetLayout(
        sheetState = modalBottomSheetState,
        sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        sheetBackgroundColor = MaterialTheme.colors.background,
        sheetContent = {
            Box(modifier = Modifier.heightIn(min = 1.dp)) {
                when {
                    moreMenu > 0 -> {
                        MoreMenuSheet(
                            onEvent = onEvent,
                            projectId = projectId,
                            moreMenu = moreMenu,
                            modalBottomSheetState = modalBottomSheetState,
                            coroutineScope = coroutineScope,
                            onNavigateToAddEditProject = onNavigateToAddEditProject
                        )
                    }

                    addEditTaskSheetVis -> {
                        AddEditTaskSheet(
                            onEvent = onEvent,
                            title = taskTitle,
                            selectedTask = selectedTask,
                            modalBottomSheetState = modalBottomSheetState,
                            coroutineScope = coroutineScope
                        )
                    }
                }
            }
        }
    ) {
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
                        IconButton(
                            onClick = {
                                onEvent(ProjectDetailEvent.OnMoreMenuClicked(1))

                                if (!modalBottomSheetState.isAnimationRunning) {
                                    coroutineScope.launch {
                                        modalBottomSheetState.show()
                                    }
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.MoreVert,
                                contentDescription = "Project more option icon"
                            )
                        }
                    }
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        onEvent(ProjectDetailEvent.OnAddEditTaskSheetVisChanged(true))
                        onEvent(ProjectDetailEvent.OnTaskSelected(null))

                        if (!modalBottomSheetState.isAnimationRunning) {
                            coroutineScope.launch {
                                modalBottomSheetState.show()
                            }
                        }
                    }
                ) {
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
                    .pullRefresh(pullRefreshState)
            ) {
                LazyColumn {
                    when (projectDetailState) {
                        UIState.Loading -> {
                            item {
                                ProjectDetailShimmer()
                                Spacer(modifier = Modifier.height(25.dp))
                                TaskItemCardShimmer(modifier = Modifier.padding(horizontal = 20.dp))
                            }
                        }

                        is UIState.Success -> {
                            onEvent(ProjectDetailEvent.OnPullRefresh(false))
                            onReloadedChanged(false)

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
                                            Row(
                                                modifier = Modifier.weight(1f),
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                AsyncImage(
                                                    modifier = Modifier
                                                        .size(60.dp)
                                                        .clip(MaterialTheme.shapes.medium),
                                                    model = ImageRequest.Builder(LocalContext.current)
                                                        .data(project.icon)
                                                        .build(),
                                                    placeholder = painterResource(
                                                        id = R.drawable.img_default_project_icon
                                                    ),
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
                                            Spacer(modifier = Modifier.width(10.dp))
                                            Label(
                                                text = stringResource(
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
                                                        text = project.platform,
                                                        backgroundColor = MaterialTheme.colors.secondary,
                                                        textColor = MaterialTheme.colors.secondaryVariant
                                                    )
                                                    Spacer(modifier = Modifier.width(10.dp))
                                                    Label(
                                                        text = project.category,
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

                                tasksSection(
                                    onEvent = onEvent,
                                    tasks = tasks,
                                    modalBottomSheetState = modalBottomSheetState,
                                    coroutineScope = coroutineScope
                                )
                            }
                        }

                        is UIState.Fail -> {
                            onEvent(ProjectDetailEvent.OnPullRefresh(false))
                            onReloadedChanged(false)

                            item {
                                LaunchedEffect(scaffoldState) {
                                    projectDetailState.message?.let {
                                        scaffoldState.snackbarHostState.showSnackbar(it)
                                    }
                                }
                            }
                        }

                        is UIState.Error -> {
                            onEvent(ProjectDetailEvent.OnPullRefresh(false))
                            onReloadedChanged(false)

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

                PullRefreshIndicator(
                    modifier = Modifier.align(Alignment.TopCenter),
                    refreshing = pullRefreshing,
                    state = pullRefreshState,
                    contentColor = MaterialTheme.colors.primary
                )
            }

            // Observe modalBottomSheetState current value
            LaunchedEffect(modalBottomSheetState.currentValue) {
                if (modalBottomSheetState.currentValue == ModalBottomSheetValue.Hidden) {
                    onEvent(ProjectDetailEvent.OnMoreMenuClicked(0))
                    onEvent(ProjectDetailEvent.OnAddEditTaskSheetVisChanged(false))
                    onEvent(ProjectDetailEvent.OnTaskTitleChanged(""))
                    onEvent(ProjectDetailEvent.OnTaskSelected(null))
                }
            }

            // Delete project confirmation dialog
            if (deleteProjectDialogVis) {
                CustomAlertDialog(
                    title = stringResource(id = R.string.delete_project),
                    message = stringResource(id = R.string.delete_project_confirm_msg),
                    onConfirmed = {
                        onEvent(ProjectDetailEvent.OnDeleteProjectDialogVisChanged(false))
                        onEvent(ProjectDetailEvent.DeleteProject)
                    },
                    onDismissed = {
                        onEvent(ProjectDetailEvent.OnDeleteProjectDialogVisChanged(false))
                    }
                )
            }

            // Delete project confirmation dialog
            if (deleteTaskDialogVis) {
                CustomAlertDialog(
                    title = stringResource(id = R.string.delete_task),
                    message = stringResource(id = R.string.delete_task_confirm_msg),
                    onConfirmed = {
                        onEvent(ProjectDetailEvent.OnDeleteTaskDialogVisChanged(false))
                    },
                    onDismissed = {
                        onEvent(ProjectDetailEvent.OnDeleteTaskDialogVisChanged(false))
                    }
                )
            }

            // Observe reloaded state from SharedViewModel
            if (reloaded) {
                onEvent(ProjectDetailEvent.GetProjectDetail)
            }

            // Observe delete project state
            when (deleteProjectState) {
                UIState.Loading -> {
                    ProgressBarWithBackground()
                }

                is UIState.Success -> {
                    LaunchedEffect(Unit) {
                        onReloadedChanged(true)
                        onNavigateUp()
                    }
                }

                is UIState.Fail -> {
                    LaunchedEffect(scaffoldState) {
                        deleteProjectState.message?.let {
                            scaffoldState.snackbarHostState.showSnackbar(it)
                        }
                    }
                }

                is UIState.Error -> {
                    LaunchedEffect(scaffoldState) {
                        deleteProjectState.message?.let {
                            scaffoldState.snackbarHostState.showSnackbar(it)
                        }
                    }
                }

                else -> {}
            }

            // Observe add task state
            when (addTaskState) {
                UIState.Loading -> {
                    ProgressBarWithBackground()
                }

                is UIState.Success -> {
                    LaunchedEffect(Unit) {
                        onEvent(ProjectDetailEvent.GetProjectDetail)
                    }
                }

                is UIState.Fail -> {
                    LaunchedEffect(scaffoldState) {
                        addTaskState.message?.let {
                            scaffoldState.snackbarHostState.showSnackbar(it)
                        }
                    }
                }

                is UIState.Error -> {
                    LaunchedEffect(scaffoldState) {
                        addTaskState.message?.let {
                            scaffoldState.snackbarHostState.showSnackbar(it)
                        }
                    }
                }

                else -> {}
            }

            // Observe edit task state
            when (editTaskState) {
                UIState.Loading -> {
                    // This will be triggered if only a task is edited
                    if (addEditTaskSheetVis) {
                        ProgressBarWithBackground()
                    }
                }

                is UIState.Success -> {
                    LaunchedEffect(Unit) {
                        // This will be triggered if only a task is edited
                        if (addEditTaskSheetVis) {
                            onEvent(ProjectDetailEvent.GetProjectDetail)
                        }
                    }
                }

                is UIState.Fail -> {
                    LaunchedEffect(scaffoldState) {
                        editTaskState.message?.let {
                            scaffoldState.snackbarHostState.showSnackbar(it)
                        }
                    }
                }

                is UIState.Error -> {
                    LaunchedEffect(scaffoldState) {
                        editTaskState.message?.let {
                            scaffoldState.snackbarHostState.showSnackbar(it)
                        }
                    }
                }

                else -> {}
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
private fun LazyListScope.tasksSection(
    onEvent: (ProjectDetailEvent) -> Unit,
    tasks: List<TaskItem>,
    modalBottomSheetState: ModalBottomSheetState,
    coroutineScope: CoroutineScope
) {
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

    if (tasks.isNotEmpty()) {
        itemsIndexed(tasks) { i, taskItem ->
            TaskItemCard(
                modifier = Modifier.padding(horizontal = 20.dp),
                taskItem = taskItem,
                onChecked = {
                    onEvent(
                        ProjectDetailEvent.OnTaskChecked(
                            index = i,
                            task = taskItem.copy(
                                status = if (taskItem.status == TaskStatus.UNDONE) {
                                    TaskStatus.DONE
                                } else {
                                    TaskStatus.UNDONE
                                }
                            )
                        )
                    )

                    onEvent(ProjectDetailEvent.EditTask)
                },
                onMoreClicked = {
                    onEvent(ProjectDetailEvent.OnTaskSelected(taskItem))
                    onEvent(ProjectDetailEvent.OnMoreMenuClicked(2))

                    if (!modalBottomSheetState.isAnimationRunning) {
                        coroutineScope.launch {
                            modalBottomSheetState.show()
                        }
                    }
                }
            )
            Spacer(modifier = Modifier.height(15.dp))
        }

        item {
            Spacer(modifier = Modifier.height(45.dp))
        }
    } else {
        item {
            CaptionImage(
                modifier = Modifier.size(200.dp),
                image = painterResource(id = R.drawable.illustration_add_note),
                caption = stringResource(id = R.string.no_tasks)
            )
        }
    }
}