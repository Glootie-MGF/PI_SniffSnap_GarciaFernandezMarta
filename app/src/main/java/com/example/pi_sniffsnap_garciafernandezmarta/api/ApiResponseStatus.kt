package com.example.pi_sniffsnap_garciafernandezmarta.api

/*
enum class ApiResponseStatus {
    LOADING,
    ERROR,
    SUCCESS
}*/
sealed class ApiResponseStatus<T>() {
    class Loading<T>: ApiResponseStatus<T>()
    class Success<T>(val data: T): ApiResponseStatus<T>()
    class Error<T>(val messageId: Int): ApiResponseStatus<T>()
}