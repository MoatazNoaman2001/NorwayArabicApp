package com.example.norwayapplicationxml.peresentation.youtube

import com.example.norwayapplicationxml.common.Resource
import com.example.norwayapplicationxml.domain.model.YoutubeVideo
import kotlinx.coroutines.flow.MutableStateFlow

interface YoutubeViewModel {

    fun getYoutubeList() : MutableStateFlow<Resource<List<YoutubeVideo> , Exception>?>
}