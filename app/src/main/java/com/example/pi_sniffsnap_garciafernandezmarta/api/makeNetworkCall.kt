package com.example.pi_sniffsnap_garciafernandezmarta.api

import com.example.pi_sniffsnap_garciafernandezmarta.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.net.UnknownHostException

private const val UNAUTHORIZED_ERR_CODE = 401
suspend fun <T> makeNetworkCall(
    call: suspend () -> T
): ApiResponseStatus<T> {
    return withContext(Dispatchers.IO){
        try {
            ApiResponseStatus.Success(call())
        } catch (e: UnknownHostException){
            ApiResponseStatus.Error(R.string.error_unknown_host_exc)
        } catch (e: retrofit2.HttpException){
            val errorMessage = if (e.code() == UNAUTHORIZED_ERR_CODE){
                R.string.wrong_user_or_password
            } else {
                R.string.unknown_error
            }
            ApiResponseStatus.Error(errorMessage)
        } catch (e: Exception){
            val errorMessage = when(e.message){
                "sign_up_error" -> R.string.error_sign_up
                "sign_in_error" -> R.string.error_sign_in
                "user_already_exists" -> R.string.user_already_exists
                else -> R.string.unknown_error
            }
            ApiResponseStatus.Error(errorMessage)
        }
    }
}