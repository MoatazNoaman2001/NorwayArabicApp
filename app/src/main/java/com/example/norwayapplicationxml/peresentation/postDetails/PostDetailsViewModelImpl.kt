package com.example.norwayapplicationxml.peresentation.postDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.norwayapplicationxml.common.Resource
import com.example.norwayapplicationxml.domain.model.New
import com.example.norwayapplicationxml.domain.useCase.GetPostDetailsUseCase
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PostDetailsViewModelImpl : PostDetailsViewModel, ViewModel() {
    private val postDetailsFlow: GetPostDetailsUseCase;
    private var new : ((Resource<New, Exception>)->Unit)?= null

    init {
        postDetailsFlow = GetPostDetailsUseCase()
    }

    fun getNew(new : ((Resource<New , Exception>)->Unit)){
        this.new = new
    }
    override fun getPostDetails(link: String) {
        viewModelScope.launch {
            postDetailsFlow(link).collectLatest {
                new?.invoke(it)
            }
        }
    }
}