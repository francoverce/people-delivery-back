package com.example.userint.v1

import com.example.userint.domain.entities.Claim
import com.example.userint.domain.entities.Trips
import com.example.userint.domain.requests.ClaimDTO
import com.example.userint.domain.requests.ClaimReponse
import com.example.userint.domain.requests.TripsDTO
import com.example.userint.domain.requests.toClaimReponse
import com.example.userint.jwt.ITokenExtractor
import com.example.userint.jwt.JwtTokenUtil
import com.example.userint.services.ClaimService
import com.example.userint.services.TripService
import io.swagger.annotations.Api
import model.GenericUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.servlet.http.HttpServletRequest

@CrossOrigin
@RestController
@Api(value = "", tags = ["Claims"], protocols = "https")
@RequestMapping("/claims")
class JWTClaimsController {

    @Autowired
    private lateinit var claimService: ClaimService

    @Autowired
    @Qualifier("jwtHeaderTokenExtractor")
    lateinit var tokenExtractor: ITokenExtractor

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/create")
    fun createClaim(
        request: HttpServletRequest,
        @RequestBody claim: ClaimDTO
    ): ResponseEntity<ClaimReponse> {
        var userCode: UUID? = null

        val tokenPayload = tokenExtractor.Extract(request.getHeader(JwtTokenUtil.TOKEN_HEADER))
        val claims = tokenExtractor.ReadToken(tokenPayload)

        if (!claims.isNullOrEmpty()) {
            if (GenericUtils.getValueFromKeyValue(claims, "userCode") != null) {
                userCode = UUID.fromString(GenericUtils.getValueFromKeyValue(claims, "userCode").toString())
            }
        }
        return ResponseEntity(claimService.create(claim, userCode!!), HttpStatus.CREATED)
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping
    fun getClaim(
        request: HttpServletRequest,
    ): ResponseEntity<List<ClaimReponse?>> {
        var userCode: UUID? = null

        val tokenPayload = tokenExtractor.Extract(request.getHeader(JwtTokenUtil.TOKEN_HEADER))
        val claims = tokenExtractor.ReadToken(tokenPayload)

        if (!claims.isNullOrEmpty()) {
            if (GenericUtils.getValueFromKeyValue(claims, "userCode") != null) {
                userCode = UUID.fromString(GenericUtils.getValueFromKeyValue(claims, "userCode").toString())
            }
        }
        return ResponseEntity(claimService.getClaims(userCode!!).map { it?.toClaimReponse() }, HttpStatus.OK)
    }
}
