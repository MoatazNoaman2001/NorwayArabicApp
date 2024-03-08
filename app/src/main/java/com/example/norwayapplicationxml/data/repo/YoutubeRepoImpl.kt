package com.example.norwayapplicationxml.data.repo

import com.example.norwayapplicationxml.common.Resource
import com.example.norwayapplicationxml.domain.model.YoutubeVideo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup

class YoutubeRepoImpl : YoutubeRepo {
    override suspend fun getYoutubeVideos(): Resource<List<YoutubeVideo>, Exception> {
        val youtubeVideos = ArrayList<YoutubeVideo>()
        return try{
            val result = withContext(Dispatchers.IO) {
                val dc = Jsoup.connect("https://norwayvoice.no/?playlist=1068eef&video=c0a80b1").get()
                val youtubeBlock = dc.getElementsByClass("e-tabs-main-area")
                val youtubeLinkBlock = dc.getElementsByClass("e-tabs-content-wrapper").map { it.children().map { it.attr("data-video-url") } }.flatten()
                val itemCount= youtubeBlock.map {  it.getElementsByClass("e-tabs-items").get(0).children().size}.first()
                for (i in 0 until itemCount) {
                    val title = youtubeBlock.map {  it.getElementsByClass("e-tabs-items").get(0).getElementsByClass("e-tab-title-text").select("a").map { it.text() }.get(i)}.firstOrNull()
                    val thumbnail = youtubeBlock.map { it.getElementsByClass("e-tabs-items").get(0).getElementsByClass("e-tab-thumbnail").select("img").map { it.attr("src") }.get(i) }.firstOrNull()
                    val duration = youtubeBlock.map { it.getElementsByClass("e-tabs-items").get(0).getElementsByClass("e-tab-duration").map { it.text() }.getOrElse(i){""} }.firstOrNull()
                    val link = youtubeLinkBlock[i]
                    youtubeVideos.add(YoutubeVideo(thumbnail?:"" , title?:"" , duration?:"" , link?:""))

                }
                youtubeVideos
            }
            Resource.success(result)
        }catch (e:Exception){
            Resource.failed(e)
        }
    }

}