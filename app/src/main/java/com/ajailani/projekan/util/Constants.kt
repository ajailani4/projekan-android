package com.ajailani.projekan.util

class Constants {
    object DataStore {
        const val PREFERENCES_NAME = "projekanPreferences"
        const val ACCESS_TOKEN_KEY = "accessToken"
        const val CONVERSION_DATA_KEY = "conversionData"
    }

    object List {
        val platforms = listOf("Android", "Cross-platform", "Web", "Desktop", "Others")
        val categories = listOf(
            "Application",
            "UI/UX Design",
            "Website",
            "Prototype",
            "Design",
            "Video",
            "Others"
        )
    }
}