package com.example.norwayapplicationxml.peresentation.onBoard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.norwayapplicationxml.data.NewsPagingSource

class OnBoardNewsViewModel constructor() : ViewModel() {
    val  news = Pager(
        PagingConfig(pageSize = 10, enablePlaceholders = false)
    ) {
        NewsPagingSource("https://norwayvoice.no/category/%d8%a7%d9%84%d8%aa%d8%ad%d9%82%d9%8a%d9%82%d8%a7%d8%aa/")
    }.flow.cachedIn(viewModelScope)
}