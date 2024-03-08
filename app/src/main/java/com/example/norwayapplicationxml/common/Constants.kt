package com.example.norwayapplicationxml.common

import java.util.regex.Matcher
import java.util.regex.Pattern

object Constants {
    val selectedLanguage= "Language"
    val app = "App"

    val dateFormate = "yyyy-MM-dd'T'HH:mm:ssXXX"

    fun extractYTId(ytUrl: String?): String? {
        val pattern = "(?<=youtu.be/|watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*"
        val compiledPattern = Pattern.compile(pattern)
        val matcher: Matcher = compiledPattern.matcher(ytUrl)
        return if (matcher.find()) {
            matcher.group()
        } else {
            "error"
        }
    }
}