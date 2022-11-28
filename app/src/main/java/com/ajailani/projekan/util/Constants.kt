package com.ajailani.projekan.util

import com.ajailani.projekan.domain.model.Project

class Constants {
    object DataStore {
        const val PREFERENCES_NAME = "projekanPreferences"
        const val ACCESS_TOKEN_KEY = "accessToken"
    }
}

val projects = listOf(
    Project(
        id = "1",
        title = "Projekan",
        description = "Hehe",
        platform = "Mobile",
        category = "Application",
        deadline = "2022-12-04",
        icon = "icon.png"
    ),
    Project(
        id = "2",
        title = "Projekan",
        description = "Hehe",
        platform = "Mobile",
        category = "Application",
        deadline = "2022-12-04",
        icon = "icon.png"
    ),
    Project(
        id = "3",
        title = "Projekan",
        description = "Hehe",
        platform = "Mobile",
        category = "Application",
        deadline = "2022-12-04",
        icon = "icon.png"
    ),
    Project(
        id = "4",
        title = "Projekan",
        description = "Hehe",
        platform = "Mobile",
        category = "Application",
        deadline = "2022-12-04",
        icon = "icon.png"
    )
)