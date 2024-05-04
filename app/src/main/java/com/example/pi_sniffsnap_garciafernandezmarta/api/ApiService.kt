package com.example.pi_sniffsnap_garciafernandezmarta.api

import com.example.pi_sniffsnap_garciafernandezmarta.BASE_URL
import com.example.pi_sniffsnap_garciafernandezmarta.GET_ALL_DOGS_URL
import com.example.pi_sniffsnap_garciafernandezmarta.SIGN_UP_URL
import com.example.pi_sniffsnap_garciafernandezmarta.SING_IN_URL
import com.example.pi_sniffsnap_garciafernandezmarta.api.dto.LoginDTO
import com.example.pi_sniffsnap_garciafernandezmarta.api.dto.SignUpDTO
import com.example.pi_sniffsnap_garciafernandezmarta.api.responses.DogListApiResponse
import com.example.pi_sniffsnap_garciafernandezmarta.api.responses.AuthApiResponse
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create())
    .build()

interface ApiService {
    // Realizamos las llamadas a la API
    @GET(GET_ALL_DOGS_URL)
    suspend fun getAllDogs() : DogListApiResponse

    @POST(SIGN_UP_URL)
    suspend fun signUp(@Body signUpDTO: SignUpDTO): AuthApiResponse

    @POST(SING_IN_URL)
    suspend fun login(@Body loginDTO: LoginDTO): AuthApiResponse

}

object DogsApi {
    val retrofitService : ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}