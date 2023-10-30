package com.example.userint.repositories.sql


import com.example.userint.domain.entities.Users
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RegisterRepository : JpaRepository<Users, Long> {

    fun findByEmail(email: String): Users?
}
