package com.ajailani.projekan.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
//import com.ajailani.projekan.ui.feature.add_edit_project.AddEditProjectScreen
import com.ajailani.projekan.ui.feature.home.HomeScreen
import com.ajailani.projekan.ui.feature.login.LoginScreen
import com.ajailani.projekan.ui.feature.project_detail.ProjectDetailScreen
import com.ajailani.projekan.ui.feature.project_list.ProjectListScreen
import com.ajailani.projekan.ui.feature.register.RegisterScreen
import com.ajailani.projekan.ui.feature.welcome.WelcomeScreen

@Composable
fun Navigation(
    navController: NavHostController,
    startDestination: String
) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable(route = Screen.WelcomeScreen.route) {
            WelcomeScreen(
                onNavigateToLogin = {
                    navController.navigate(Screen.LoginScreen.route)
                },
                onNavigateToRegister = {
                    navController.navigate(Screen.RegisterScreen.route)
                }
            )
        }

        composable(route = Screen.LoginScreen.route) {
            LoginScreen(
                onNavigateUp = {
                    navController.navigateUp()
                },
                onNavigateToRegister = {
                    navController.navigate(Screen.RegisterScreen.route)
                },
                onNavigateToHome = {
                    navController.navigate(Screen.HomeScreen.route) {
                        launchSingleTop = true

                        popUpTo(Screen.WelcomeScreen.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(route = Screen.RegisterScreen.route) {
            RegisterScreen(
                onNavigateUp = {
                    navController.navigateUp()
                },
                onNavigateToLogin = {
                    navController.navigate(Screen.LoginScreen.route)
                },
                onNavigateToHome = {
                    navController.navigate(Screen.HomeScreen.route) {
                        launchSingleTop = true

                        popUpTo(Screen.WelcomeScreen.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(route = Screen.HomeScreen.route) {
            HomeScreen(
                onNavigateToProjectList = { projectType ->
                    navController.navigate(
                        Screen.ProjectListScreen.route + "?projectType=$projectType"
                    )
                },
                onNavigateToProjectDetail = { projectId ->
                    navController.navigate(
                        Screen.ProjectDetailScreen.route + "?projectId=$projectId"
                    )
                }
            )
        }

        composable(
            route = Screen.ProjectListScreen.route + "?projectType={projectType}",
            arguments = listOf(
                navArgument("projectType") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                }
            )
        ) {
            ProjectListScreen(
                onNavigateUp = {
                    navController.navigateUp()
                },
                onNavigateToProjectDetail = { projectId ->
                    navController.navigate(
                        Screen.ProjectDetailScreen.route + "?projectId=$projectId"
                    )
                }
            )
        }

        composable(
            route = Screen.ProjectDetailScreen.route + "?projectId={projectId}",
            arguments = listOf(
                navArgument("projectId") {
                    type = NavType.StringType
                }
            )
        ) {
            ProjectDetailScreen(
                onNavigateUp = {
                    navController.navigateUp()
                }
            )
        }

        composable(route = Screen.AddEditProjectScreen.route) {
            /*AddEditProjectScreen(
                onNavigateUp = {
                    navController.navigateUp()
                }
            )*/
        }
    }
}