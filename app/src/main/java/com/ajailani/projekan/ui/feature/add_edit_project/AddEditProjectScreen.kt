package com.ajailani.projekan.ui.feature.add_edit_project

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Photo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ajailani.projekan.R
import com.ajailani.projekan.ui.common.UIState
import com.ajailani.projekan.ui.common.component.ProgressBarWithBackground
import com.ajailani.projekan.ui.feature.add_edit_project.component.OutlinedChip
import com.ajailani.projekan.ui.feature.add_edit_project.component.showDatePicker
import com.ajailani.projekan.ui.theme.extraLarge
import com.ajailani.projekan.util.Constants
import com.ajailani.projekan.util.Formatter
import com.ajailani.projekan.util.convertInputStreamToFile
import com.google.accompanist.flowlayout.FlowRow
import id.zelory.compressor.Compressor
import kotlinx.coroutines.launch

@Composable
fun AddEditProjectScreen(
    addProjectViewModel: AddProjectViewModel = hiltViewModel(),
    onNavigateUp: () -> Unit
) {
    val onEvent = addProjectViewModel::onEvent
    val addProjectState = addProjectViewModel.addProjectState
    val title = addProjectViewModel.title
    val description = addProjectViewModel.description
    val platform = addProjectViewModel.platform
    val category = addProjectViewModel.category
    val deadline = addProjectViewModel.deadline
    val icon = addProjectViewModel.icon

    val coroutineScope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()

    val context = LocalContext.current

    val pickMediaLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            if (uri != null) {
                val inputStream = context.contentResolver.openInputStream(uri)

                if (inputStream != null) {
                    coroutineScope.launch {
                        onEvent(
                            AddProjectEvent.OnIconChanged(
                                Compressor.compress(
                                    context,
                                    context.convertInputStreamToFile(inputStream)
                                )
                            )
                        )
                    }
                }
            }
        }
    )

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                elevation = 0.dp,
                title = {
                    Text(
                        text = stringResource(id = R.string.add_project)
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
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.project_icon),
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.padding(5.dp))
                Box {
                    AsyncImage(
                        modifier = Modifier
                            .size(110.dp)
                            .clip(MaterialTheme.shapes.medium),
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(icon ?: R.drawable.img_default_project_icon)
                            .build(),
                        contentScale = ContentScale.Crop,
                        contentDescription = "Project icon"
                    )
                    Button(
                        modifier = Modifier
                            .size(42.dp)
                            .align(Alignment.BottomEnd)
                            .offset(x = 15.dp, y = 15.dp),
                        shape = MaterialTheme.shapes.extraLarge,
                        elevation = ButtonDefaults.elevation(defaultElevation = 0.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
                        contentPadding = PaddingValues(0.dp),
                        onClick = {
                            pickMediaLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Photo,
                            contentDescription = "Icon of add project icon"
                        )
                    }
                }
                Spacer(modifier = Modifier.height(30.dp))
                Text(
                    text = stringResource(id = R.string.title),
                    fontWeight = FontWeight.Bold
                )
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = title,
                    onValueChange = { onEvent(AddProjectEvent.OnTitleChanged(it)) },
                    colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent),
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(30.dp))
                Text(
                    text = stringResource(id = R.string.description),
                    fontWeight = FontWeight.Bold
                )
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = description,
                    onValueChange = { onEvent(AddProjectEvent.OnDescriptionChanged(it)) },
                    colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent),
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(30.dp))
                Text(
                    text = stringResource(id = R.string.platform),
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.padding(5.dp))
                FlowRow(mainAxisSpacing = 8.dp) {
                    Constants.List.platforms.forEach {
                        OutlinedChip(
                            text = it,
                            isSelected = it == platform,
                            onClick = { onEvent(AddProjectEvent.OnPlatformChanged(it)) }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(30.dp))
                Text(
                    text = stringResource(id = R.string.category),
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.padding(5.dp))
                FlowRow(mainAxisSpacing = 8.dp) {
                    Constants.List.categories.forEach {
                        OutlinedChip(
                            text = it,
                            isSelected = it == category,
                            onClick = { onEvent(AddProjectEvent.OnCategoryChanged(it)) }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(30.dp))
                Text(
                    text = stringResource(id = R.string.deadline),
                    fontWeight = FontWeight.Bold
                )
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            context.showDatePicker {
                                onEvent(AddProjectEvent.OnDeadlineChanged(it))
                            }
                        },
                    value = if (deadline != "") Formatter.formatDate(deadline) else "",
                    onValueChange = {},
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.CalendarMonth,
                            contentDescription = "Date icon"
                        )
                    },
                    enabled = false,
                    colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent),
                    textStyle = MaterialTheme.typography.body1.copy(
                        color = MaterialTheme.colors.onBackground
                    ),
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(30.dp))
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.extraLarge,
                    onClick = {
                        if (title.isNotEmpty() && description.isNotEmpty() &&
                            platform.isNotEmpty() && category.isNotEmpty() && deadline.isNotEmpty()
                        ) {
                            onEvent(AddProjectEvent.AddProject)
                        } else {
                            coroutineScope.launch {
                                scaffoldState.snackbarHostState.showSnackbar(
                                    context.getString(R.string.fill_the_form)
                                )
                            }
                        }
                    }
                ) {
                    Text(
                        modifier = Modifier.padding(5.dp),
                        text = stringResource(id = R.string.save),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        // Observe add project state
        when (addProjectState) {
            UIState.Loading -> {
                ProgressBarWithBackground()
            }

            is UIState.Success -> {
                onNavigateUp()
            }

            is UIState.Fail -> {
                LaunchedEffect(scaffoldState) {
                    addProjectState.message?.let {
                        scaffoldState.snackbarHostState.showSnackbar(it)
                    }
                }
            }

            is UIState.Error -> {
                LaunchedEffect(scaffoldState) {
                    addProjectState.message?.let {
                        scaffoldState.snackbarHostState.showSnackbar(it)
                    }
                }
            }

            else -> {}
        }
    }
}