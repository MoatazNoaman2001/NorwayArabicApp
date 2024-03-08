package com.example.norwayapplicationxml.data

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.norwayapplicationxml.common.Resource
import com.example.norwayapplicationxml.data.repo.NewsRepoImpl
import com.example.norwayapplicationxml.domain.model.New

private const val TAG = "NewsPagingSource"

class NewsPagingSource (val url : String) : PagingSource<Int, New>(){
    override fun getRefreshKey(state: PagingState<Int, New>): Int? {
        return state.anchorPosition?.let {
            val anchorPos = state.closestPageToPosition(it)
            anchorPos?.prevKey?.plus(1)?:anchorPos?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, New> {
        val key = params.key?:1
        val newsRepoImpl = NewsRepoImpl()
        val resource = newsRepoImpl.getNewsList("$url/page/$key/")
//        val max_key = withContext(Dispatchers.IO){
//            val dc = Jsoup.connect("https://norwayvoice.no/category/%d9%85%d9%86%d9%88%d8%b9%d8%a7%d8%aa/").get()
//            dc.getElementsByClass("magmax_pagination").select("li").filter { it: Element ->
//                try{it.tagName("a").text().toInt() > 0}catch (e:Exception){false}
//            }.maxOfOrNull { it.tagName("a").text().toInt() }
//        }
        Log.d(TAG, "load: $resource")
        return when (resource) {
            is Resource.failed -> {
                LoadResult.Error(resource.messaage)
            }

            is Resource.success -> {
                LoadResult.Page(
                    data = resource.data, prevKey = null, nextKey = key + 1
                )
            }
        }

    }
}