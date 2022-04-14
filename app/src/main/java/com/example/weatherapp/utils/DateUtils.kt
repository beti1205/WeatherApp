package com.example.weatherapp.utils

import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

private val shortTimeFormatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)
private val timeFormatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM)
private val dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
private val dayNameFormatter = DateTimeFormatter.ofPattern("E")

val ZonedDateTime.formattedDate: String
    get() = format(dateFormatter)

val ZonedDateTime.formattedTime: String
    get() = format(timeFormatter)

val ZonedDateTime.formattedDayName: String
    get() = format(dayNameFormatter)

val ZonedDateTime.formattedTimeShort: String
    get() = format(shortTimeFormatter)

fun Long.toZoneDateTime(timeZone: String): ZonedDateTime {
    return Instant.ofEpochSecond(this)
        .atZone(ZoneId.of(timeZone))
}