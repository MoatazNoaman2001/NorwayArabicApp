package com.example.norwayapplicationxml.peresentation.commons

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.norwayapplicationxml.domain.useCase.ChangeLanguageUseCase
import com.example.norwayapplicationxml.domain.useCase.ChangeThemeUseCase
import com.example.norwayapplicationxml.domain.useCase.GetThemeUseCase
import com.example.norwayapplicationxml.domain.useCase.getLanguageUseCase
import com.example.norwayapplicationxml.peresentation.startApp.selectLanguage.SelectLanguageViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject

private const val TAG = "MainActivityViewModel"

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val changeLanguageUseCase: ChangeLanguageUseCase,
    private val getLanguageUseCase: getLanguageUseCase,
    private val getThemeUseCase: GetThemeUseCase,
    private val setThemeUseCase: ChangeThemeUseCase
) : SelectLanguageViewModel, SelectThemeViewModel, ViewModel() {
    private var language: ((String?) -> Unit)? = null
    private var theme: ((String?) -> Unit)? = null

    init {
        CoroutineScope(Dispatchers.Main).launch {
            getLanguageUseCase().collectLatest {
                language?.invoke(it)
                Log.d(TAG, "lang: $it, language: $language")
            }
        }
        CoroutineScope(Dispatchers.Main).launch {
            getThemeUseCase().collectLatest {
                Log.d(TAG, "theme: $it")
                theme?.invoke(it)
            }
        }
    }

    override fun selectLanguage(language: String) {
        viewModelScope.launch {
            changeLanguageUseCase(language)
        }
    }

    override fun getAppLanguage(lang: ((String?) -> Unit)?) {
        this.language = lang
    }

    override fun currentLanguage(): String? {
        return runBlocking {
            getLanguageUseCase().first()
        }
    }

    override fun getTheme(theme: ((String?) -> Unit)?) {
        this.theme = theme
    }

    override fun setTheme(v: Int) {
        viewModelScope.launch {
            setThemeUseCase(v)
        }
    }

}