package com.example.userint.domain.responses

data class RegisterResponse(
    var code: String,
    var message: String,
    val userCaseId: String,
    val sessionEnc: String?,
)
