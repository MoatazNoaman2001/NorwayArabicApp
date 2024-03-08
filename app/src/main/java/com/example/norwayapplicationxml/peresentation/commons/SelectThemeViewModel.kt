package com.example.norwayapplicationxml.peresentation.commons

interface SelectThemeViewModel {
    fun getTheme(theme : ((String?)->Unit)?)
    fun setTheme(v:Int)
}
