package com.example.userint.repositories.sql


import com.example.userint.domain.entities.Claim
import com.example.userint.domain.entities.Trips
import com.example.userint.domain.entities.Users
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ClaimRepository : JpaRepository<Claim, Long> {

    fun findByUserId_Code(userCode: UUID): List<Claim?>


}
