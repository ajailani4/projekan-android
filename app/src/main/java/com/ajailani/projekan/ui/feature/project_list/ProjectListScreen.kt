package com.ajailani.projekan.ui.feature.project_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ajailani.projekan.ui.common.component.VProjectCard
import com.ajailani.projekan.ui.theme.backgroundGrey
import com.ajailani.projekan.util.projects

@Composable
fun ProjectListScreen() {
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                elevation = 0.dp,
                title = {
                    Text(
                        text = "This Week Deadlines"
                    )
                },
                backgroundColor = MaterialTheme.colors.surface,
                navigationIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
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
        ) {
            LazyColumn(contentPadding = PaddingValues(20.dp)) {
                items(projects) { project ->
                    VProjectCard(
                        project = project,
                        onClick = {}
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewProjectListScreen() {
    ProjectListScreen()
}