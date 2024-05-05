package com.example.pi_sniffsnap_garciafernandezmarta.api

import com.example.pi_sniffsnap_garciafernandezmarta.ADD_DOG_TO_USER_URL
import com.example.pi_sniffsnap_garciafernandezmarta.BASE_URL
import com.example.pi_sniffsnap_garciafernandezmarta.GET_ALL_DOGS_URL
import com.example.pi_sniffsnap_garciafernandezmarta.SIGN_UP_URL
import com.example.pi_sniffsnap_garciafernandezmarta.SING_IN_URL
import com.example.pi_sniffsnap_garciafernandezmarta.api.dto.AddDogToUserDTO
import com.example.pi_sniffsnap_garciafernandezmarta.api.dto.LoginDTO
import com.example.pi_sniffsnap_garciafernandezmarta.api.dto.SignUpDTO
import com.example.pi_sniffsnap_garciafernandezmarta.api.responses.DogListApiResponse
import com.example.pi_sniffsnap_garciafernandezmarta.api.responses.AuthApiResponse
import com.example.pi_sniffsnap_garciafernandezmarta.api.responses.DefaultResponse
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

private val okHttpClient = OkHttpClient.Builder()
    .addInterceptor(ApiServiceInterceptor)
    .build()
private val retrofit = Retrofit.Builder()
    .client(okHttpClient)
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

    @Headers("${ApiServiceInterceptor.NEEDS_AUTH_HEADER_KEY}: true")
    @POST(ADD_DOG_TO_USER_URL)
    suspend fun addDogToUser(@Body addDogToUserDTO: AddDogToUserDTO): DefaultResponse
}

object DogsApi {
    val retrofitService : ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}