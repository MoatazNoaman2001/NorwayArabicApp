package com.example.norwayapplicationxml.domain

import android.content.Context
import android.content.ContextWrapper
import java.util.Locale

class LanguageHelper {

    companion object{
        fun changeLanguage(context: Context , languageCode:String) : ContextWrapper{
            var contextR = context
            val configuration = context.resources.configuration
            val systemLocale = configuration.locales[0]


            if (languageCode != "" && languageCode != systemLocale.language){
                val locale = Locale(languageCode)
                Locale.setDefault(locale)
                configuration.setLocale(locale)
                configuration.setLayoutDirection(locale)
                contextR = context.createConfigurationContext(configuration)
            }
            return ContextWrapper(contextR)
        }
    }
}