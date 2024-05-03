package com.example.pi_sniffsnap_garciafernandezmarta.api.dto

import com.squareup.moshi.Json

class SignUpDTO(
    val email: String,
    val password: String,
    @field:Json(name = "password_confirmation") val passwordConfirmation: String)