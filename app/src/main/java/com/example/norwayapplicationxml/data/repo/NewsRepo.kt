package com.example.norwayapplicationxml.data.repo

import com.example.norwayapplicationxml.common.Resource
import com.example.norwayapplicationxml.domain.model.New

interface NewsRepo {

    suspend fun getNewsList(url:String) :  Resource<List<New> , Exception>
    suspend fun getNewDetails(url:String) : Resource<New , Exception>

    suspend fun getNewsFromBanal(url: String) : Resource<List<New> , Exception>
}