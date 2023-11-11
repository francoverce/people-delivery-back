package com.example.userint.v1

import com.example.userint.domain.entities.Trips
import com.example.userint.domain.requests.*
import com.example.userint.jwt.ITokenExtractor
import com.example.userint.jwt.JwtTokenUtil
import com.example.userint.services.TripService
import io.swagger.annotations.Api
import model.GenericUtils
import org.json.JSONObject
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
@Api(value = "", tags = ["Trips"], protocols = "https")
@RequestMapping("/trips")
class JWTTripController {

    @Autowired
    private lateinit var tripService: TripService

    @Autowired
    @Qualifier("jwtHeaderTokenExtractor")
    lateinit var tokenExtractor: ITokenExtractor

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/create")
    fun createTrip(
        request: HttpServletRequest,
        @RequestBody trip: TripsDTO): ResponseEntity<Trips> {
        var userCode: UUID? = null

        val tokenPayload = tokenExtractor.Extract(request.getHeader(JwtTokenUtil.TOKEN_HEADER))
        val claims = tokenExtractor.ReadToken(tokenPayload)

        if (!claims.isNullOrEmpty()) {
            if (GenericUtils.getValueFromKeyValue(claims, "userCode") != null) {
                userCode = UUID.fromString(GenericUtils.getValueFromKeyValue(claims, "userCode").toString())
            }
        }
        return ResponseEntity(tripService.step1(trip, userCode!!), HttpStatus.CREATED)
    }

    @PreAuthorize("hasRole('USER')")
    @PatchMapping("/cancel/{code}/trip/{status}/status")
    fun cancelTrip(
        request: HttpServletRequest,
        @PathVariable("code") code: UUID,
        @PathVariable("status") status: String): ResponseEntity<Trips> {
        var userCode: UUID? = null

        val tokenPayload = tokenExtractor.Extract(request.getHeader(JwtTokenUtil.TOKEN_HEADER))
        val claims = tokenExtractor.ReadToken(tokenPayload)

        if (!claims.isNullOrEmpty()) {
            if (GenericUtils.getValueFromKeyValue(claims, "userCode") != null) {
                userCode = UUID.fromString(GenericUtils.getValueFromKeyValue(claims, "userCode").toString())
            }
        }
        return ResponseEntity(tripService.cancelTrip(code,status, userCode!!), HttpStatus.OK)
    }

    @PostMapping("accepted/events")
    fun acceptedTrip(
        request: HttpServletRequest,
        @RequestBody event: AcceptedTrip
    ): ResponseEntity<Unit> {
        return ResponseEntity(tripService.acceptedTrip(event), HttpStatus.OK)    }

    @PostMapping("ongoing/events")
    fun ongoinTrip(
        request: HttpServletRequest,
        @RequestBody event: OnGoingTrip
    ): ResponseEntity<Unit> {
        return ResponseEntity(tripService.ongoinTrip(event), HttpStatus.OK)    }

    @PostMapping("ongoing/events")
    fun closedTrip(
        request: HttpServletRequest,
        @RequestBody event: ClosedTrip
    ): ResponseEntity<Unit> {
        return ResponseEntity(tripService.closedTrip(event), HttpStatus.OK)    }
}
