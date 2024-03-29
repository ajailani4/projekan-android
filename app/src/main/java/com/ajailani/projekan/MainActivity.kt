package com.ajailani.projekan

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.ajailani.projekan.ui.Navigation
import com.ajailani.projekan.ui.Screen
import com.ajailani.projekan.ui.common.SharedViewModel
import com.ajailani.projekan.ui.feature.splash.SplashViewModel
import com.ajailani.projekan.ui.theme.ProjekanTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val splashViewModel: SplashViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by viewModels {
        SavedStateViewModelFactory(application, this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        lifecycleScope.launch {
            splashViewModel.getAccessToken().first().let { accessToken ->
                val startDestination = if (accessToken != "") {
                    Screen.Home.route
                } else {
                    Screen.Welcome.route
                }

                setContent {
                    ProjekanTheme {
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = MaterialTheme.colors.background
                        ) {
                            Content(
                                startDestination = startDestination,
                                sharedViewModel = sharedViewModel
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Content(
    startDestination: String,
    sharedViewModel: SharedViewModel
) {
    val navController = rememberNavController()

    Navigation(
        navController = navController,
        startDestination = startDestination,
        sharedViewModel = sharedViewModel
    )
}