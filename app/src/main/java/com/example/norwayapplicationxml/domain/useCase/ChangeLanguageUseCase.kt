package com.example.norwayapplicationxml.domain.useCase

import android.content.Context
import android.content.SharedPreferences
import com.example.norwayapplicationxml.common.Constants
import com.example.norwayapplicationxml.common.di.AppPrefViewModelScope
import com.example.norwayapplicationxml.domain.SettingManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ChangeLanguageUseCase @Inject constructor(
    @ApplicationContext val context: Context
) {
    suspend operator fun invoke(language:String) {
        SettingManager(context).saveSelectedLanguage(language)
    }
}