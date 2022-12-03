package com.ajailani.projekan.ui.feature.project_detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.ajailani.projekan.R
import com.ajailani.projekan.ui.common.component.Label
import com.ajailani.projekan.ui.feature.project_detail.component.TaskItemCard
import com.ajailani.projekan.ui.feature.project_detail.component.tasks
import com.ajailani.projekan.ui.theme.Blue
import com.ajailani.projekan.ui.theme.Grey
import com.ajailani.projekan.ui.theme.Yellow
import com.ajailani.projekan.ui.theme.backgroundGrey
import com.ajailani.projekan.util.Formatter

@Composable
fun ProjectDetailScreen(
    onNavigateUp: () -> Unit
) {
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
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colors.backgroundGrey)
                .padding(innerPadding)
        ) {
            LazyColumn {
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
                                Image(
                                    modifier = Modifier
                                        .size(60.dp)
                                        .clip(MaterialTheme.shapes.medium),
                                    painter = painterResource(id = R.drawable.app_icon),
                                    contentDescription = "Project icon"
                                )
                                Spacer(modifier = Modifier.width(20.dp))
                                Text(
                                    text = "Projekan",
                                    color = MaterialTheme.colors.onSurface,
                                    style = MaterialTheme.typography.h3
                                )
                            }
                            Label(
                                title = "In Progress",
                                backgroundColor = Yellow,
                                textColor = Color.White
                            )
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                        Text(
                            text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam eget varius massa, tempor pretium nunc. Maecenas luctus condimentum ultrices.",
                            color = MaterialTheme.colors.onSurface
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        Row {
                            Label(
                                title = "Mobile",
                                backgroundColor = MaterialTheme.colors.secondary,
                                textColor = MaterialTheme.colors.secondaryVariant
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Label(
                                title = "Application",
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
                                    append(Formatter.formatDate("2022-12-12"))
                                }
                            },
                            style = MaterialTheme.typography.body1.copy(
                                fontWeight = FontWeight.Medium
                            )
                        )
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(25.dp))
                }

                tasksSection()
            }
        }
    }
}

private fun LazyListScope.tasksSection() {
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