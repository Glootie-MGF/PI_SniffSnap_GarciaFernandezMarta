package com.example.pi_sniffsnap_garciafernandezmarta.api

import com.example.pi_sniffsnap_garciafernandezmarta.BASE_URL
import com.example.pi_sniffsnap_garciafernandezmarta.Dog
import com.example.pi_sniffsnap_garciafernandezmarta.GET_ALL_DOGS_URL
import com.example.pi_sniffsnap_garciafernandezmarta.api.responses.DogListApiResponse
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create())
    .build()

interface ApiService {
    // Realizamos las llamadas a la API
    @GET(GET_ALL_DOGS_URL)
    suspend fun getAllDogs() : DogListApiResponse
}

object DogsApi {
    val retrofitService : ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}