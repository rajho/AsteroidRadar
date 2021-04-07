package com.udacity.asteroidradar.utils

import java.text.SimpleDateFormat
import java.util.*


fun Calendar.getCurrentDate(pattern: String) : String {
    val current = Calendar.getInstance()
    val formatter = SimpleDateFormat(pattern, Locale.getDefault())

    return formatter.format(current.time)
}
