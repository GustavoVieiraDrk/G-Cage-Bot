package com.gacagebot.localizestrings

import java.util.ResourceBundle
import java.util.Locale

object LocalizeString {
    private const val RESOURCE_BASE_NAME = "values.strings"
    private val resourceBundle = ResourceBundle.getBundle(RESOURCE_BASE_NAME, Locale.getDefault())

    fun get(stringId: String): String {
        return resourceBundle.getString(stringId) ?: "Error: Failed to get string"
    }
}
