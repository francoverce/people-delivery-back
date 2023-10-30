package com.example.userint.repositories.sql

import com.example.userint.domain.entities.Users
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository : JpaRepository<Users, Long> {

    fun findByCode(userCode: UUID): Users

    fun deleteByCode(userCode: UUID)

    fun findByEmail(email: String): Users
}
