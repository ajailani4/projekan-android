package com.ajailani.projekan.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ajailani.projekan.ui.feature.home.HomeScreen
import com.ajailani.projekan.ui.feature.login.LoginScreen
import com.ajailani.projekan.ui.feature.register.RegisterScreen
import com.ajailani.projekan.ui.feature.welcome.WelcomeScreen

@Composable
fun Navigation(
    navController: NavHostController,
    startDestination: String
) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable(Screen.WelcomeScreen.route) {
            WelcomeScreen(
                onNavigateToLogin = {
                    navController.navigate(Screen.LoginScreen.route)
                },
                onNavigateToRegister = {
                    navController.navigate(Screen.RegisterScreen.route)
                }
            )
        }

        composable(Screen.LoginScreen.route) {
            LoginScreen(
                onNavigateUp = {
                    navController.navigateUp()
                },
                onNavigateToRegister = {
                    navController.navigate(Screen.RegisterScreen.route)
                }
            )
        }

        composable(Screen.RegisterScreen.route) {
            RegisterScreen(
                onNavigateUp = {
                    navController.navigateUp()
                },
                onNavigateToLogin = {
                    navController.navigate(Screen.LoginScreen.route)
                }
            )
        }

        composable(Screen.HomeScreen.route) {
            HomeScreen()
        }
    }
}