package com.ajailani.projekan.ui.feature.project_detail.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ajailani.projekan.R
import com.ajailani.projekan.ui.feature.project_detail.ProjectDetailEvent
import com.ajailani.projekan.ui.theme.extraLarge

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AddEditTaskSheet(
    onEvent: (ProjectDetailEvent) -> Unit,
    projectId: String?,
    modalBottomSheetState: ModalBottomSheetState
) {
    val coroutineScope = rememberCoroutineScope()
    
    Column(modifier = Modifier.padding(20.dp)) {
        Text(
            text = stringResource(id = R.string.add_task),
            fontWeight = FontWeight.SemiBold,
            style = MaterialTheme.typography.subtitle1
        )
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = "",
            onValueChange = {},
            colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent)
        )
        Spacer(modifier = Modifier.height(30.dp))
        Button(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.extraLarge,
            onClick = { /*TODO*/ }
        ) {
            Text(
                modifier = Modifier.padding(5.dp),
                text = stringResource(id = R.string.save),
                textAlign = TextAlign.Center
            )
        }
    }
}