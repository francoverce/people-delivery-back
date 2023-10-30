package com.example.userint.repositories.sql

import com.example.userint.domain.entities.Reviews
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ReviewsRepository : JpaRepository<Reviews, Long> {

    fun findByRestaurantCode(restaurantCode: UUID): List<Reviews>?

    fun findByUserCode(restaurantCode: UUID): List<Reviews?>
}
