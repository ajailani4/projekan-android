package com.ajailani.projekan.ui.feature.project_detail.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ajailani.projekan.R
import com.ajailani.projekan.ui.feature.project_detail.ProjectDetailEvent
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MoreMenuSheet(
    onEvent: (ProjectDetailEvent) -> Unit,
    projectId: String?,
    modalBottomSheetState: ModalBottomSheetState,
    moreMenu: Int,
    onNavigateToAddEditProject: (String) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier.padding(vertical = 10.dp)) {
        BottomSheetItem(
            icon = Icons.Default.Edit,
            title = stringResource(id = R.string.edit),
            onClick = {
                coroutineScope.launch {
                    modalBottomSheetState.hide()
                }

                when (moreMenu) {
                    1 -> {
                        projectId?.let { id ->
                            onNavigateToAddEditProject(id)
                        }
                    }

                    2 -> {}
                }
            }
        )
        BottomSheetItem(
            icon = Icons.Default.Delete,
            title = stringResource(id = R.string.delete),
            onClick = {
                coroutineScope.launch {
                    modalBottomSheetState.hide()
                }

                when (moreMenu) {
                    1 -> {
                        onEvent(ProjectDetailEvent.OnDeleteProjectDialogVisChanged(true))
                    }

                    2 -> {}
                }
            }
        )
    }
}