package com.example.pi_sniffsnap_garciafernandezmarta.api.responses

import com.example.pi_sniffsnap_garciafernandezmarta.api.dto.DogDTO
import com.squareup.moshi.Json

class DogApiResponse (
    val message: String,
    @field:Json(name = "is_success") val isSuccess: Boolean,
    val data: DogResponse
)