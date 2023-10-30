package com.example.userint.services

import com.example.userint.domain.entities.Claim
import com.example.userint.domain.entities.Trips
import com.example.userint.domain.requests.ClaimDTO
import com.example.userint.domain.requests.ClaimReponse
import com.example.userint.domain.requests.TripsDTO
import com.example.userint.domain.requests.toClaimReponse
import com.example.userint.repositories.sql.ClaimRepository
import com.example.userint.repositories.sql.TripRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.*

@Service
class ClaimService  {

    @Autowired
    private lateinit var claimRepository: ClaimRepository

    @Autowired
    private lateinit var userService: UserService

    fun create(claim: ClaimDTO, userCode: UUID): ClaimReponse {
        userService.getUser(userCode).let {
          return  claimRepository.save(
              Claim(
                    userId = it,
                    created_at = Instant.now(),
                    is_finished = false,
                    title = claim.title,
                    description = claim.description,
                    tripCode = claim.tripCode,
                    status = "OPEN",
                  driverName = claim.driverName
                )
            ).toClaimReponse()
        }
    }

    fun getClaims(userCode: UUID): List<Claim?> {
        return claimRepository.findByUserId_Code(userCode)
    }
    }
