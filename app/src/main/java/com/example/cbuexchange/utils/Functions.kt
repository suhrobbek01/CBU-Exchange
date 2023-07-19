package com.example.cbuexchange.utils

import android.content.Context
import android.os.Build
import java.util.Locale

fun getCurrentLocale(context: Context): Locale? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        context.resources.configuration.locales[0]
    } else {
        context.resources.configuration.locale
    }
}