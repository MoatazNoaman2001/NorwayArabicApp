package com.example.norwayapplicationxml.domain.useCase

import com.example.norwayapplicationxml.common.Resource
import com.example.norwayapplicationxml.data.repo.NewsRepoImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.shareIn

class GetPostDetailsUseCase {
    private val nrepo: NewsRepoImpl;

    init {
        nrepo = NewsRepoImpl()
    }


    operator fun invoke(link:String) = flow {
        emit(nrepo.getNewDetails(link))
    }
}