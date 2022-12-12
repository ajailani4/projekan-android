package com.ajailani.projekan.ui.feature.project_detail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.RadioButtonUnchecked
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.ajailani.projekan.domain.model.TaskItem
import com.ajailani.projekan.ui.theme.BackgroundShimmer
import com.ajailani.projekan.ui.theme.Grey
import com.ajailani.projekan.util.TaskStatus
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer

@Composable
fun TaskItemCard(
    modifier: Modifier = Modifier,
    taskItem: TaskItem,
    onChecked: () -> Unit,
    onMoreClicked: () -> Unit
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.large,
        elevation = 0.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = taskItem.title
            )
            Spacer(modifier = Modifier.width(10.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    modifier = Modifier.size(24.dp),
                    onClick = onChecked
                ) {
                    Icon(
                        imageVector = if (taskItem.status == TaskStatus.DONE) {
                            Icons.Default.CheckCircle
                        } else {
                            Icons.Outlined.RadioButtonUnchecked
                        },
                        tint = if (taskItem.status == TaskStatus.DONE) {
                            MaterialTheme.colors.primary
                        } else {
                            Grey
                        },
                        contentDescription = "Check icon"
                    )
                }
                Spacer(modifier = Modifier.width(7.dp))
                IconButton(
                    modifier = Modifier.size(24.dp),
                    onClick = onMoreClicked
                ) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        tint = Grey,
                        contentDescription = "Task more option icon"
                    )
                }
            }
        }
    }
}

@Composable
fun TaskItemCardShimmer(modifier: Modifier = Modifier) {
    val shimmerInstance = rememberShimmer(shimmerBounds = ShimmerBounds.View)

    for (i in 1..3) {
        Card(
            modifier = modifier.shimmer(shimmerInstance),
            shape = MaterialTheme.shapes.large,
            elevation = 0.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.small)
                        .size(width = 200.dp, height = 20.dp)
                        .background(color = BackgroundShimmer)
                        .shimmer(shimmerInstance)
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .clip(MaterialTheme.shapes.small)
                            .size(24.dp)
                            .background(color = BackgroundShimmer)
                            .shimmer(shimmerInstance)
                    )
                    Spacer(modifier = Modifier.width(7.dp))
                    Box(
                        modifier = Modifier
                            .clip(MaterialTheme.shapes.small)
                            .size(24.dp)
                            .background(color = BackgroundShimmer)
                            .shimmer(shimmerInstance)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
    }
}