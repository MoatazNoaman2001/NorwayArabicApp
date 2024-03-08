package com.example.norwayapplicationxml.common

sealed class Resource<T, R>(data:T? = null , message:R?= null) {
    data class success<T , R>(val data: T) : Resource<T , R>(data = data)
    data class failed<T, R>(val messaage: R) : Resource<T ,R>(message = messaage)
}