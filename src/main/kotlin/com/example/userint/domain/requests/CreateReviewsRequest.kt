package com.example.userint.domain.requests

import com.example.userint.domain.entities.Reviews
import java.util.*

data class   CreateReviewsRequest(
    val restaurantCode: UUID,
    val rating: Int,
    val comment: String,
    val description: String?,
    val image: String?,
    val username: String?
) {
    fun toModel(userCode: UUID) = Reviews(
        userCode = userCode,
        restaurantCode = restaurantCode,
        rating = rating,
        comment = comment,
        description = description,
        image = image,
        userName = username
    )
}
