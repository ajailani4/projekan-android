package com.ajailani.projekan.ui

sealed class Screen(val route: String) {
    object Welcome : Screen("welcome_screen")
    object Login : Screen("login_screen")
    object Register : Screen("register_screen")
    object Home : Screen("home_screen")
    object ProjectList : Screen("project_list_screen")
    object ProjectDetail : Screen("project_detail_screen")
    object AddEditProject : Screen("add_edit_project_screen")
}
