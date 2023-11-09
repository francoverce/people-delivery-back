package com.example.userint.domain.model

data class TokenRequest(
    val id: Int,
    val code: String
)

data class TokenReponse(
    val success: Boolean,
    val code: String,
)