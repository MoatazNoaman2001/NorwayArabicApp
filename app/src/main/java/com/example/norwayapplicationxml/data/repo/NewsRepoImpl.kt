package com.example.norwayapplicationxml.data.repo

import android.system.Os.link
import androidx.recyclerview.widget.RecyclerView
import com.example.norwayapplicationxml.common.Resource
import com.example.norwayapplicationxml.domain.model.New
import com.example.norwayapplicationxml.domain.model.WebPair
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import java.util.Deque
import java.util.concurrent.atomic.AtomicReference


class NewsRepoImpl : NewsRepo {

    //general news link "https://norwayvoice.no/category/%d9%85%d9%86%d9%88%d8%b9%d8%a7%d8%aa/"
    override suspend fun getNewsList(url: String): Resource<List<New>, Exception> {
        return try {
            val generalNewsList = ArrayList<New>()
            val result = withContext(Dispatchers.IO) {
                val dc: Document = Jsoup.connect(url).get()
                val list: List<Element> =
                    dc.getElementsByClass("col-md-8 blog-content").select("article").toList()
                for (element in list) {
                    val entryTitle: String =
                        element.getElementsByClass("entry-title").tagName("a").text()
                    val by: String = element.getElementsByClass("url fn n").text()
                    val date: String =
                        element.getElementsByClass("entry-date published").attr("datetime")
                    val views: String = element.getElementsByClass("post-views").text()
                    val readingDuration: String =
                        element.getElementsByClass("reading_duration").text()
                    val image: String =
                        element.getElementsByClass("attachment-full size-full wp-post-image")
                            .attr("src")
                    val content: String =
                        element.getElementsByClass("post-excerpt").tagName("p").text()
                    val cat: List<WebPair> = element.getElementsByClass("cat-links").select("a")
                        .map { WebPair(it.attr("href"), it.text()) }

                    val link: String =
                        element.getElementsByClass("post-full-article-link").select("a")
                            .attr("href")
                    val postNew =
                        New(entryTitle, by, views, readingDuration, date, image, content, cat, link)
                    generalNewsList.add(postNew)
                }

                generalNewsList
            }

            Resource.success(result)
        } catch (e: Exception) {
            Resource.failed(e)
        }
    }

    override suspend fun getNewDetails(url: String): Resource<New, Exception> {
       return try{
           val result = withContext(Dispatchers.IO){
               val element = Jsoup.connect(url).get()
               val imageUrl =
                   element.getElementsByClass("attachment-full size-full wp-post-image").attr("src")
               val title = element.getElementsByClass("entry-title").text()
               val likes = element.getElementsByClass("count-thumbsup").toList().map { it.select("span").text() }.get(0)
               val postedOn = element.getElementsByClass("entry-date published").attr("datetime")
               val byline = element.getElementsByClass("byline").text()
               val views = element.getElementsByClass("post-views").text()
               val durations = element.getElementsByClass("reading_duration").toList().map { it.text() }.get(0)
               var publisherImage = ""
               var publisherLink = ""
               element.getElementsByClass("entry-content")
                   .select("p")
                   .stream()
                   .filter { it: Element ->
                       !it.getElementsByClass(
                           "alignright wp-image-11815 size-thumbnail"
                       ).attr("src").isEmpty()
                   }
                   .forEach { it: Element ->
                       publisherLink = it.select("a").attr("href")
                       publisherImage =
                           it.getElementsByClass("alignright wp-image-11815 size-thumbnail")
                               .attr("src")
                   }
               val wholeContent = element.getElementsByClass("entry-content")
                   .select("p")
                   .map { it -> it.text() }.toList()
               val articleContent = wholeContent.takeWhile { !it.contains("المحرر المسؤول") }.toList()
               val footerContent =
                   wholeContent.dropWhile { s: String -> !s.contains("المحرر المسؤول") }.toList()
               val links: List<WebPair> = element.getElementsByClass("entry-content").select("a")
                   .map { it: Element -> WebPair(it.text(), it.attr("href")) }.toList()
               val cats = element.getElementsByClass("cat-links").select("a").map { it: Element -> WebPair(it.attr("href"), it.text()) }.toList()
               val tags = element.getElementsByClass("tags-links").select("a").map { it: Element ->
                       WebPair(it.attr("href"), it.text())
                   }.toList()

               New(title, byline, votes = views,readDuration = durations, postedOn, likes.toInt(), imageUrl, articleContent, footerContent, publisher = WebPair(publisherLink, publisherImage), youtubeLink = links.last(), cats = cats, tags)
           }

           Resource.success(result)
       }catch (e:Exception){
           Resource.failed(e)
       }
    }

    override suspend fun getNewsFromBanal(url: String): Resource<List<New>, Exception> {
        return try {
            val result = withContext(Dispatchers.IO) {
                val dc = Jsoup.connect(url).get()
                val newsBody = dc.getElementsByClass("swiper-wrapper").select("div").map { it.children().toList() }.get(0)
                val news = newsBody.map {
                    val image = it.getElementsByClass("anwp-pg-post-teaser__thumbnail position-relative")
                        .select("img")
                        .attr("src")
                    val title = it.getElementsByClass("anwp-pg-post-teaser__title anwp-font-heading mt-2")
                        .select("a")
                        .text()

                    val date = it.getElementsByClass("anwp-pg-post-teaser__bottom-meta d-flex flex-wrap")
                        .select("span")
                        .select("time")
                        .text()

                    val subtitle = it.getElementsByClass("anwp-pg-post-teaser__excerpt mb-2")
                        .select("p")
                        .text()

                    val link = it.getElementsByClass("w-100 anwp-pg-read-more mt-auto")
                        .select("a")
                        .attr("href")

                    val new = New(title, subtitle, date, image, link)

                    new
                }
                news
            }
            Resource.success(result)
        } catch (e: Exception) {
            Resource.failed(e)
        }
    }
}