package com.ajailani.projekan.ui.feature.add_edit_project.component

import android.app.DatePickerDialog
import android.content.Context
import java.util.*

fun Context.showDatePicker(onDateChanged: (String) -> Unit) {
    val calendar = Calendar.getInstance()
    val calYear = calendar.get(Calendar.YEAR)
    val calMonth = calendar.get(Calendar.MONTH)
    val calDay = calendar.get(Calendar.DAY_OF_MONTH)

    DatePickerDialog(
        this,
        { _, year, month, dayOfMonth ->
            onDateChanged(String.format("%02d-%02d-%02d", year, month + 1, dayOfMonth))
        },
        calYear,
        calMonth,
        calDay
    ).show()
}