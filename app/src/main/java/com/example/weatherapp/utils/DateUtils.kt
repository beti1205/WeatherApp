package com.example.weatherapp.utils

import java.text.SimpleDateFormat
import java.util.*

private val shortTimeFormat = SimpleDateFormat.getTimeInstance(SimpleDateFormat.SHORT)
private val timeFormat = SimpleDateFormat.getTimeInstance()
private val dateFormat = SimpleDateFormat.getDateInstance()
private val dayNameFormat = SimpleDateFormat("E", Locale.getDefault())


val Date.formattedDate: String
    get() = dateFormat.format(this)

val Date.formattedTime: String
    get() = timeFormat.format(this)

val Date.formattedDayName: String
    get() = dayNameFormat.format(this)

val Date.formattedTimeShort: String
    get() = shortTimeFormat.format(this)