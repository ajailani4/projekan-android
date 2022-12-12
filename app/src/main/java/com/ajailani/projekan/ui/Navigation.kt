package com.ajailani.projekan.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.ajailani.projekan.ui.common.SharedViewModel
import com.ajailani.projekan.ui.feature.add_edit_project.AddEditProjectScreen
import com.ajailani.projekan.ui.feature.home.HomeScreen
import com.ajailani.projekan.ui.feature.login.LoginScreen
import com.ajailani.projekan.ui.feature.project_detail.ProjectDetailScreen
import com.ajailani.projekan.ui.feature.project_list.ProjectListScreen
import com.ajailani.projekan.ui.feature.register.RegisterScreen
import com.ajailani.projekan.ui.feature.welcome.WelcomeScreen

@Composable
fun Navigation(
    navController: NavHostController,
    startDestination: String,
    sharedViewModel: SharedViewModel
) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable(route = Screen.Welcome.route) {
            WelcomeScreen(
                onNavigateToLogin = {
                    navController.navigate(Screen.Login.route)
                },
                onNavigateToRegister = {
                    navController.navigate(Screen.Register.route)
                }
            )
        }

        composable(route = Screen.Login.route) {
            LoginScreen(
                onNavigateUp = {
                    navController.navigateUp()
                },
                onNavigateToRegister = {
                    navController.navigate(Screen.Register.route)
                },
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        launchSingleTop = true

                        popUpTo(Screen.Welcome.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(route = Screen.Register.route) {
            RegisterScreen(
                onNavigateUp = {
                    navController.navigateUp()
                },
                onNavigateToLogin = {
                    navController.navigate(Screen.Login.route)
                },
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        launchSingleTop = true

                        popUpTo(Screen.Welcome.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(route = Screen.Home.route) {
            HomeScreen(
                sharedViewModel = sharedViewModel,
                onNavigateToProjectList = { projectType ->
                    navController.navigate(
                        Screen.ProjectList.route + "?projectType=$projectType"
                    )
                },
                onNavigateToProjectDetail = { projectId ->
                    navController.navigate(
                        Screen.ProjectDetail.route + "?projectId=$projectId"
                    )
                },
                onNavigateToAddEditProject = {
                    navController.navigate(
                        Screen.AddEditProject.route
                    )
                }
            )
        }

        composable(
            route = Screen.ProjectList.route + "?projectType={projectType}",
            arguments = listOf(
                navArgument("projectType") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                }
            )
        ) {
            ProjectListScreen(
                sharedViewModel = sharedViewModel,
                onNavigateUp = {
                    navController.navigateUp()
                },
                onNavigateToProjectDetail = { projectId ->
                    navController.navigate(
                        Screen.ProjectDetail.route + "?projectId=$projectId"
                    )
                }
            )
        }

        composable(
            route = Screen.ProjectDetail.route + "?projectId={projectId}",
            arguments = listOf(
                navArgument("projectId") {
                    type = NavType.StringType
                }
            )
        ) {
            ProjectDetailScreen(
                sharedViewModel = sharedViewModel,
                onNavigateUp = {
                    navController.navigateUp()
                },
                onNavigateToAddEditProject = { projectId ->
                    navController.navigate(
                        Screen.AddEditProject.route + "?projectId=$projectId"
                    )
                }
            )
        }

        composable(
            route = Screen.AddEditProject.route + "?projectId={projectId}",
            arguments = listOf(
                navArgument("projectId") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                }
            )
        ) {
            AddEditProjectScreen(
                sharedViewModel = sharedViewModel,
                onNavigateUp = {
                    navController.navigateUp()
                }
            )
        }
    }
}