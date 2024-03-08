package com.example.norwayapplicationxml.data.repo

import com.example.norwayapplicationxml.common.Resource
import com.example.norwayapplicationxml.domain.model.YoutubeVideo

interface YoutubeRepo {

    suspend fun getYoutubeVideos(): Resource<List<YoutubeVideo>, Exception>
}