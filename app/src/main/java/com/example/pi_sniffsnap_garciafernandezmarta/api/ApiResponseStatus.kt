package com.example.pi_sniffsnap_garciafernandezmarta.api

import com.example.pi_sniffsnap_garciafernandezmarta.Dog

/*
enum class ApiResponseStatus {
    LOADING,
    ERROR,
    SUCCESS
}*/
sealed class ApiResponseStatus() {
    class Loading(): ApiResponseStatus()
    class Success(val dogList: List<Dog>): ApiResponseStatus()
    class Error(val messageId: Int): ApiResponseStatus()
}