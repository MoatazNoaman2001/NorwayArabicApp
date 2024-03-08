package com.example.norwayapplicationxml.domain.model

import java.io.Serializable

data class YoutubeVideo(
    val thumbnail: String,
    val title:String,
    val duration :String,
    val link :String
) :Serializable