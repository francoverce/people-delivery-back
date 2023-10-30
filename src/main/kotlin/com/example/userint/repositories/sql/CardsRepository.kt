package com.example.userint.repositories.sql


import com.example.userint.domain.entities.Cards
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CardsRepository : JpaRepository<Cards, Long>{
    fun findByUserId_Code (userCode: UUID): List<Cards>

    fun findByUserId_CodeAndCardNumber (userCode: UUID, cardNumber:String): Cards
}