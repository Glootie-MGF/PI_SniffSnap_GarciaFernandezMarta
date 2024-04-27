package com.example.pi_sniffsnap_garciafernandezmarta.api

import com.example.pi_sniffsnap_garciafernandezmarta.R
import com.example.pi_sniffsnap_garciafernandezmarta.api.dto.DogDTOMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.net.UnknownHostException

suspend fun <T> makeNetworkCall(
    call: suspend () -> T
): ApiResponseStatus<T> {
    return withContext(Dispatchers.IO){
        try {
            ApiResponseStatus.Success(call())
        } catch (e: Exception){
            ApiResponseStatus.Error(R.string.unknown_error)
        } catch (e: UnknownHostException){
            ApiResponseStatus.Error(R.string.error_unknown_host_exc)
        }
    }
}