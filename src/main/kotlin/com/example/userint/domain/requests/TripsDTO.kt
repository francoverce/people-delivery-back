package com.example.userint.domain.requests

data class TripsDTO(
    val from: String,
    val since: String,
    val distance: Float,
    val paymentMethod: String?,
)
