package com.ajailani.projekan.ui.feature.project_detail.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.RadioButtonUnchecked
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.ajailani.projekan.domain.model.TaskItem
import com.ajailani.projekan.ui.theme.Grey
import com.ajailani.projekan.util.TaskStatus

@Composable
fun TaskItemCard(
    modifier: Modifier = Modifier,
    taskItem: TaskItem,
    onChecked: (Boolean) -> Unit,
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
                text = taskItem.title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconToggleButton(
                    modifier = Modifier.size(24.dp),
                    checked = taskItem.status == TaskStatus.DONE,
                    onCheckedChange = onChecked
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