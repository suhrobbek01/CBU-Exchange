package com.example.cbuexchange.utils

import android.content.Context
import android.os.Build
import java.util.Locale

data class LocaleHelper(val SELECTED_LANGUAGE: String = "Locale.Helper.Selected.Language") {

    fun setLocale(context: Context, language: String): Context {
        persist(context, language)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return updateResources(context, language)
        }
        return updateResourcesLegacy(context, language)
    }

    private fun updateResourcesLegacy(context: Context, language: String): Context {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val configuration = context.resources.configuration
        configuration.setLocale(locale)
        return context.createConfigurationContext(configuration)
    }

    private fun updateResources(context: Context, language: String): Context {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val resources = context.resources
        val configuration = resources.configuration
        configuration.locale = locale
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLayoutDirection(locale)
        }
        resources.updateConfiguration(configuration, resources.displayMetrics)
        return context
    }

    private fun persist(context: Context, language: String) {
        val mySharedPreference = MySharedPreference(context)
        mySharedPreference.setPreferences(SELECTED_LANGUAGE, language)
    }
}