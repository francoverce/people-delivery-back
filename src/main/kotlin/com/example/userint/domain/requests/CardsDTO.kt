package com.example.userint.domain.requests

data class CardsDTO(
    val cardNumber: String,
    val cardOperator: String,
    val cardExpirationDate: String,
    val cardCVV: String,
)
