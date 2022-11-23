package com.example.weatherapp.utils

import android.content.Context
import android.os.Build
import java.util.*

fun Context.getCurrentLocale(): Locale {
    val configuration = resources.configuration
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        configuration.locales[0]
    } else {
        configuration.locale
    }
}
