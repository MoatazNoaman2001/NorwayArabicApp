package com.example.norwayapplicationxml.peresentation.startApp.selectLanguage

import kotlinx.coroutines.flow.Flow

interface SelectLanguageViewModel {
    fun selectLanguage(language: String)
    fun getAppLanguage(lang : ((String?)->Unit)?)

    fun currentLanguage() : String?
}