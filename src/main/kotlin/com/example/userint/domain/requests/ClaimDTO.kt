package com.example.userint.domain.requests

import com.example.userint.domain.entities.Claim
import java.time.ZoneId
import java.time.format.DateTimeFormatter


data class ClaimDTO(
    val driverName : String?,
    val tripCode : String,
    val title: String,
    val description: String,
)

data class ClaimReponse(
    val driverName: String?,
    val tripCode : String,
    val title: String,
    val description: String,
    val createdAt: String,
    val updateAt: String,
    val status: String,
)

fun Claim.toClaimReponse(): ClaimReponse {
    return ClaimReponse(
        driverName = driverName,
        tripCode = tripCode,
        title = title,
        description = description,
        createdAt = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(created_at.atZone(ZoneId.systemDefault())),
        updateAt = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(updated_at.atZone(ZoneId.systemDefault())),
        status = status,
    )
}

