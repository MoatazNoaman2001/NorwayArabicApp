package com.example.norwayapplicationxml.peresentation.commons

import com.example.norwayapplicationxml.common.Resource
import com.example.norwayapplicationxml.data.repo.NewsRepoImpl
import com.example.norwayapplicationxml.domain.model.New
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.cache
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.shareIn
import kotlin.Exception

class GetNewsBanalUseCase {

    operator fun invoke() :Flow<Resource<List<New>, Exception>> = flow {
        emit(NewsRepoImpl().getNewsFromBanal("https://norwayvoice.no/"))
    }.catch { emit(Resource.failed(Exception(it))) }
}