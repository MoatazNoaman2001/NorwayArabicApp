package com.example.norwayapplicationxml.peresentation.news.sportNews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.norwayapplicationxml.data.NewsPagingSource

class SportNewsViewModel constructor() : ViewModel() {
    val news = Pager(
        PagingConfig(pageSize = 10, enablePlaceholders = false)
    ) {
        NewsPagingSource("https://norwayvoice.no/category/%d8%a7%d9%84%d8%a3%d8%ae%d8%a8%d8%a7%d8%b1-%d8%a7%d9%84%d8%b1%d9%8a%d8%a7%d8%b6%d9%8a%d8%a9/")
    }.flow.cachedIn(viewModelScope)
}