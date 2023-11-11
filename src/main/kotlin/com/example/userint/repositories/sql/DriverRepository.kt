package com.example.userint.repositories.sql


import com.example.userint.domain.entities.Drivers
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DriverRepository : JpaRepository<Drivers, Long> {

}
