package com.ajailani.projekan.ui.common.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun ProgressBarWithBackground() {
    Dialog(onDismissRequest = {}) {
        Surface(shape = RoundedCornerShape(10.dp)) {
            CircularProgressIndicator(modifier = Modifier.padding(20.dp))
        }
    }
}