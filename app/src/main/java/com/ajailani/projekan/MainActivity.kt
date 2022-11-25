package com.ajailani.projekan

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.ajailani.projekan.ui.Navigation
import com.ajailani.projekan.ui.Screen
import com.ajailani.projekan.ui.theme.ProjekanTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProjekanTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Content(Screen.WelcomeScreen.route)
                }
            }
        }
    }
}

@Composable
fun Content(startDestination: String) {
    val navController = rememberNavController()

    Navigation(navController = navController, startDestination = startDestination)
}