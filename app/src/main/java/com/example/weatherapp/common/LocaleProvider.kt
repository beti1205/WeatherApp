package com.example.weatherapp.common

import android.content.Context
import com.example.weatherapp.utils.getCurrentLocale
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.Locale
import javax.inject.Inject

interface LocaleProvider {
    fun getLocale(): Locale
}

class LocaleProviderImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : LocaleProvider {
    override fun getLocale(): Locale = context.getCurrentLocale()
}