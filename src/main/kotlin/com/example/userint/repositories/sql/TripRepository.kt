package com.example.userint.repositories.sql


import com.example.userint.domain.entities.Trips
import com.example.userint.domain.entities.Users
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface TripRepository : JpaRepository<Trips, Long> {

    fun findByCodeAndUserId_Code(code: UUID, userCode: UUID): Optional<Trips>

}
