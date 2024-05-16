package com.example.todo_app.utils

import java.text.SimpleDateFormat
import java.util.Locale

fun getFormattedDateTime(millis: Long, format: String) =
    SimpleDateFormat(format, Locale.getDefault()).format(millis)