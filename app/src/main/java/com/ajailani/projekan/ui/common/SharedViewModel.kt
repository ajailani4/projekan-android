package com.ajailani.projekan.ui.common

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    var reloaded by mutableStateOf(false)
        private set

    fun onReloadedChanged(isReloaded: Boolean) {
        reloaded = isReloaded
    }
}