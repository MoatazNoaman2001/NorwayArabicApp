package com.example.norwayapplicationxml.domain.useCase

import android.content.Context
import android.content.SharedPreferences
import com.example.norwayapplicationxml.common.Constants
import com.example.norwayapplicationxml.common.di.AppPrefViewModelScope
import com.example.norwayapplicationxml.common.di.SettingDataStoreViewModelScope
import com.example.norwayapplicationxml.domain.SettingManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class getLanguageUseCase @Inject constructor(
    @AppPrefViewModelScope val sharedPreferences: SharedPreferences,
    @SettingDataStoreViewModelScope val settingManager: SettingManager
) {
    operator fun invoke() = settingManager.observeLanguageFlow()
}