package com.example.pi_sniffsnap_garciafernandezmarta.api.dto

import com.squareup.moshi.Json

class UserDTO (
    val id: Long,
    val email: String,
    @field:Json(name = "authentication_token") val authenticationToken: String
)