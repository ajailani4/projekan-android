package com.ajailani.projekan.ui.feature.project_detail.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ajailani.projekan.R
import com.ajailani.projekan.domain.model.TaskItem
import com.ajailani.projekan.ui.feature.project_detail.ProjectDetailEvent
import com.ajailani.projekan.ui.theme.extraLarge
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AddEditTaskSheet(
    onEvent: (ProjectDetailEvent) -> Unit,
    title: String,
    selectedTask: TaskItem?,
    modalBottomSheetState: ModalBottomSheetState,
    coroutineScope: CoroutineScope
) {
    Column(modifier = Modifier.padding(20.dp)) {
        Text(
            text = stringResource(
                id = if (selectedTask == null) R.string.add_task else R.string.edit_task
            ),
            fontWeight = FontWeight.SemiBold,
            style = MaterialTheme.typography.subtitle1
        )
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = title,
            onValueChange = { onEvent(ProjectDetailEvent.OnTaskTitleChanged(it)) },
            colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent)
        )
        Spacer(modifier = Modifier.height(30.dp))
        Button(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.extraLarge,
            onClick = {
                if (title.isNotEmpty()) {
                    coroutineScope.launch {
                        modalBottomSheetState.hide()
                    }

                    if (selectedTask == null) {
                        onEvent(ProjectDetailEvent.AddTask)
                    } else {
                        onEvent(ProjectDetailEvent.EditTask)
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