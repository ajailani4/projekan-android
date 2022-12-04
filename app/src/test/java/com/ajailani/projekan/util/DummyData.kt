package com.ajailani.projekan.util

import com.ajailani.projekan.data.remote.dto.ProjectItemDto
import com.ajailani.projekan.data.remote.dto.UserCredentialDto
import com.ajailani.projekan.data.remote.dto.UserProfileDto
import com.ajailani.projekan.domain.model.*

val userCredentialDto = UserCredentialDto(
    username = "george",
    accessToken = "abcd"
)

val userCredential = UserCredential(accessToken = "abcd")

val userProfileDto = UserProfileDto(
    username = "george",
    name = "George",
    email = "george@email.com",
    avatar = "image.jpg"
)

val userProfile = UserProfile(
    name = "George",
    avatar = "image.jpg"
)

val projectsDto = listOf(
    ProjectItemDto(
        id = "1",
        title = "Projekan",
        description = "Project management app",
        platform = "Mobile",
        category = "Application",
        deadline = "2022-12-05",
        icon = "icon.png"
    ),
    ProjectItemDto(
        id = "2",
        title = "Projekan 2",
        description = "Project management app",
        platform = "Mobile",
        category = "Application",
        deadline = "2022-12-05",
        icon = "icon.png"
    )
)

val projects = listOf(
    ProjectItem(
        id = "1",
        title = "Projekan",
        description = "Project management app",
        platform = "Mobile",
        category = "Application",
        deadline = "2022-12-05",
        icon = "icon.png"
    ),
    ProjectItem(
        id = "2",
        title = "Projekan 2",
        description = "Project management app",
        platform = "Mobile",
        category = "Application",
        deadline = "2022-12-05",
        icon = "icon.png"
    )
)

val tasks = listOf(
    TaskItem(
        id = "1",
        projectId = "a1b2c3",
        title = "Task 1",
        status = TaskStatus.DONE
    ),
    TaskItem(
        id = "2",
        projectId = "a1b2c3",
        title = "Task 2",
        status = TaskStatus.UNDONE
    )
)

val project = Project(
    id = "1",
    title = "Projekan",
    description = "Project management app",
    platform = "Mobile",
    category = "Application",
    deadline = "2022-12-05",
    icon = "icon.png",
    progress = 50,
    status = ProjectStatus.IN_PROGRESS,
    tasks = tasks
)