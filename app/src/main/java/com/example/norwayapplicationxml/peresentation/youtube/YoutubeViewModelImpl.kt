package com.example.norwayapplicationxml.peresentation.youtube

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.norwayapplicationxml.common.Resource
import com.example.norwayapplicationxml.data.repo.YoutubeRepoImpl
import com.example.norwayapplicationxml.domain.model.YoutubeVideo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class YoutubeViewModelImpl : YoutubeViewModel , ViewModel() {
    private val _youtubeList = MutableStateFlow<Resource<List<YoutubeVideo>, Exception>?>(null)


    init {
        viewModelScope.launch {
            _youtubeList.emit(YoutubeRepoImpl().getYoutubeVideos())
        }
    }

    override fun getYoutubeList(): MutableStateFlow<Resource<List<YoutubeVideo>, Exception>?> = _youtubeList
}