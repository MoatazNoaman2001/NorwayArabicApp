package com.example.norwayapplicationxml.peresentation.general

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.norwayapplicationxml.common.Resource
import com.example.norwayapplicationxml.data.NewsPagingSource
import com.example.norwayapplicationxml.data.repo.NewsRepoImpl
import com.example.norwayapplicationxml.domain.model.New
import com.example.norwayapplicationxml.peresentation.commons.GetNewsBanalUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch

class GeneralNewsViewModel constructor() : ViewModel() {
    val  news = Pager(
        PagingConfig(pageSize = 10, enablePlaceholders = false)
    ) {
        NewsPagingSource("https://norwayvoice.no/category/%d9%85%d9%86%d9%88%d8%b9%d8%a7%d8%aa/")
    }.flow.cachedIn(viewModelScope)

    val newsBanal = MutableStateFlow<Resource<List<New> , Exception>?>(null)
    init {
        viewModelScope.launch {
            newsBanal.emit(NewsRepoImpl().getNewsFromBanal("https://norwayvoice.no/"))
        }
    }
}