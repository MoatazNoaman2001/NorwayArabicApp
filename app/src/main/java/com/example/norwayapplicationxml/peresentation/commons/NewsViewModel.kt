package com.example.norwayapplicationxml.peresentation.commons

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.norwayapplicationxml.data.NewsPagingSource

class NewsViewModel constructor() : ViewModel() {
    fun news(link:String) = Pager(
        PagingConfig(pageSize = 10, enablePlaceholders = false)
    ) {
        NewsPagingSource(link)
    }.flow.cachedIn(viewModelScope)
}