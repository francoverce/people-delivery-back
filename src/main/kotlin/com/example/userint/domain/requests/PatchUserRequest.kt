package com.example.userint.domain.requests

data class PatchUserRequest(
    val name: String?,
    val userName: String?,
    val phone: String?,
    val lastName: String?,
    val adress: String?,
)
