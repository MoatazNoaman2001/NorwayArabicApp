package com.example.norwayapplicationxml.common.di

import android.content.Context
import com.example.norwayapplicationxml.common.Constants
import com.example.norwayapplicationxml.domain.SettingManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.FragmentScoped
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class AppPrefViewModelScope

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class SettingDataStoreViewModelScope

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelComponent {

    @Provides
//    @ViewModelScoped
    @AppPrefViewModelScope
    fun getLanguagePreference(@ApplicationContext context: Context) =
        context.getSharedPreferences(Constants.app , Context.MODE_PRIVATE)

    @Provides
    @ViewModelScoped
    @SettingDataStoreViewModelScope
    fun getSettingDataStore(@ApplicationContext context: Context) = SettingManager(context)

}