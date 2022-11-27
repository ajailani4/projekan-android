package com.ajailani.projekan.ui.feature.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.ajailani.projekan.domain.model.Project
import com.ajailani.projekan.ui.theme.Grey
import com.ajailani.projekan.util.Formatter

/**
 * A card for displaying [Project] in horizontal list
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HProjectCard(
    project: Project,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.width(270.dp),
        elevation = 0.dp,
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
        ) {
            Row {
                Image(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(MaterialTheme.shapes.small),
                    painter = rememberAsyncImagePainter(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(project.icon)
                            .build()
                    ),
                    contentScale = ContentScale.Crop,
                    contentDescription = "Project icon"
                )
                Column {
                    Text(
                        text = project.title,
                        style = MaterialTheme.typography.subtitle1.copy(
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = project.description,
                        color = Grey,
                        style = MaterialTheme.typography.body1,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(30.dp))
                    Row {
                        Label(
                            title = project.platform,
                            backgroundColor = MaterialTheme.colors.secondary,
                            contentColor = MaterialTheme.colors.secondaryVariant
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Label(
                            title = project.category,
                            backgroundColor = MaterialTheme.colors.primary,
                            contentColor = MaterialTheme.colors.primaryVariant
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = buildAnnotatedString {
                            withStyle(
                                SpanStyle(
                                    color = Grey
                                )
                            ) {
                                append("Deadline: ")
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
            }
        }
    }
}

@Composable
private fun Label(
    title: String,
    backgroundColor: Color,
    contentColor: Color
) {
    Surface(
        shape = MaterialTheme.shapes.small,
        color = backgroundColor
    ) {
        Column(modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)) {
            Text(
                text = title,
                color = contentColor,
                style = MaterialTheme.typography.body2.copy(
                    fontWeight = FontWeight.Medium
                )
            )
        }
    }
}