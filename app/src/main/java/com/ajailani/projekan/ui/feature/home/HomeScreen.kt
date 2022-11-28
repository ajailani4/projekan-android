package com.ajailani.projekan.ui.feature.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.ajailani.projekan.R
import com.ajailani.projekan.ui.common.component.VProjectCard
import com.ajailani.projekan.ui.feature.home.component.HProjectCard
import com.ajailani.projekan.ui.theme.BackgroundGrey
import com.ajailani.projekan.util.projects

@Composable
fun HomeScreen() {
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add project icon"
                )
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .background(BackgroundGrey)
                .padding(innerPadding)
        ) {
            item {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colors.primary
                ) {
                    Column(modifier = Modifier.padding(vertical = 20.dp)) {
                        Header()
                        Spacer(modifier = Modifier.height(35.dp))
                        ThisWeekDeadlinesSection(onViewAllClicked = {})
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(25.dp))
            }

            myProjectsSection()
        }
    }
}

@Composable
private fun Header() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = "${stringResource(id = R.string.hello)}, George",
                color = MaterialTheme.colors.onPrimary,
                style = MaterialTheme.typography.subtitle1.copy(
                    fontWeight = FontWeight.SemiBold
                )
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = stringResource(id = R.string.manage_your_projects),
                color = MaterialTheme.colors.onPrimary,
                style = MaterialTheme.typography.body1
            )
        }
        Image(
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape),
            painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("")
                    .build()
            ),
            contentDescription = "User avatar"
        )
    }
}

@Composable
private fun ThisWeekDeadlinesSection(
    onViewAllClicked: () -> Unit
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
                style = MaterialTheme.typography.h4
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
        LazyRow(contentPadding = PaddingValues(horizontal = 20.dp)) {
            items(projects) { project ->
                HProjectCard(
                    project = project,
                    onClick = {}
                )

                if (project != projects.last()) {
                    Spacer(modifier = Modifier.width(15.dp))
                }
            }
        }
    }
}

private fun LazyListScope.myProjectsSection() {
    item {
        Text(
            modifier = Modifier.padding(horizontal = 20.dp),
            text = stringResource(id = R.string.my_projects),
            color = MaterialTheme.colors.onBackground,
            style = MaterialTheme.typography.h4
        )
    }

    item {
        Spacer(modifier = Modifier.height(15.dp))
    }

    items(projects) { project ->
        VProjectCard(
            modifier = Modifier.padding(horizontal = 20.dp),
            project = project,
            onClick = {}
        )
        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Preview
@Composable
fun PreviewHomeScreen() {
    HomeScreen()
}

