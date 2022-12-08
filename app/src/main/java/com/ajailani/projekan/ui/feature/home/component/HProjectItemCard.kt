package com.ajailani.projekan.ui.feature.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ajailani.projekan.R
import com.ajailani.projekan.domain.model.ProjectItem
import com.ajailani.projekan.ui.common.component.Label
import com.ajailani.projekan.ui.theme.BackgroundShimmer
import com.ajailani.projekan.ui.theme.Grey
import com.ajailani.projekan.util.Formatter
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer

/**
 * A card for displaying [ProjectItem] in horizontal list
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HProjectItemCard(
    projectItem: ProjectItem,
    onClick: () -> Unit
) {
    Card(
        shape = MaterialTheme.shapes.large,
        elevation = 0.dp,
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .width(280.dp)
                .padding(15.dp)
        ) {
            Row {
                AsyncImage(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(MaterialTheme.shapes.small),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(projectItem.icon)
                        .build(),
                    placeholder = painterResource(id = R.drawable.img_default_project_icon),
                    contentScale = ContentScale.Crop,
                    contentDescription = "Project icon"
                )
                Spacer(modifier = Modifier.width(15.dp))
                Column {
                    Text(
                        text = projectItem.title,
                        style = MaterialTheme.typography.subtitle1.copy(
                            fontWeight = FontWeight.SemiBold
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = projectItem.description,
                        color = Grey,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(30.dp))
                    Row {
                        Label(
                            text = projectItem.platform,
                            backgroundColor = MaterialTheme.colors.secondary,
                            textColor = MaterialTheme.colors.secondaryVariant
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Label(
                            text = projectItem.category,
                            backgroundColor = MaterialTheme.colors.primary,
                            textColor = MaterialTheme.colors.primaryVariant
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
                                append(stringResource(id = R.string.deadline))
                                append(": ")
                            }

                            withStyle(
                                SpanStyle(
                                    color = MaterialTheme.colors.onSurface
                                )
                            ) {
                                append(Formatter.formatDate(projectItem.deadline))
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
fun HProjectItemCardShimmer() {
    val shimmerInstance = rememberShimmer(shimmerBounds = ShimmerBounds.View)

    LazyRow(contentPadding = PaddingValues(horizontal = 20.dp)) {
        items(2) {
            Card(
                modifier = Modifier.shimmer(shimmerInstance),
                shape = MaterialTheme.shapes.large,
                elevation = 0.dp
            ) {
                Column(
                    modifier = Modifier
                        .width(280.dp)
                        .padding(15.dp)
                ) {
                    Row {
                        Box(
                            modifier = Modifier
                                .clip(MaterialTheme.shapes.small)
                                .size(50.dp)
                                .background(color = BackgroundShimmer)
                                .shimmer(shimmerInstance)
                        )
                        Spacer(modifier = Modifier.width(15.dp))
                        Column {
                            Box(
                                modifier = Modifier
                                    .clip(MaterialTheme.shapes.small)
                                    .size(width = 100.dp, height = 20.dp)
                                    .background(color = BackgroundShimmer)
                                    .shimmer(shimmerInstance)
                            )
                            Spacer(modifier = Modifier.height(15.dp))
                            Box(
                                modifier = Modifier
                                    .clip(MaterialTheme.shapes.small)
                                    .size(width = 120.dp, height = 20.dp)
                                    .background(color = BackgroundShimmer)
                                    .shimmer(shimmerInstance)
                            )
                            Spacer(modifier = Modifier.height(40.dp))
                            Row {
                                Box(
                                    modifier = Modifier
                                        .clip(MaterialTheme.shapes.small)
                                        .size(width = 40.dp, height = 20.dp)
                                        .background(color = BackgroundShimmer)
                                        .shimmer(shimmerInstance)
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Box(
                                    modifier = Modifier
                                        .clip(MaterialTheme.shapes.small)
                                        .size(width = 40.dp, height = 20.dp)
                                        .background(color = BackgroundShimmer)
                                        .shimmer(shimmerInstance)
                                )
                            }
                            Spacer(modifier = Modifier.height(25.dp))
                            Box(
                                modifier = Modifier
                                    .clip(MaterialTheme.shapes.small)
                                    .size(width = 100.dp, height = 20.dp)
                                    .background(color = BackgroundShimmer)
                                    .shimmer(shimmerInstance)
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.width(15.dp))
        }
    }
}