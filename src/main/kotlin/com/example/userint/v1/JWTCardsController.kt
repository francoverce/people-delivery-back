package com.example.userint.v1

import com.example.userint.domain.requests.CardsDTO
import com.example.userint.jwt.ITokenExtractor
import com.example.userint.jwt.JwtTokenUtil
import com.example.userint.services.CardsService
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
@Api(value = "", tags = ["Cards"], protocols = "https")
@RequestMapping("/cards")
class CardsController {

    @Autowired
    private lateinit var cardsService: CardsService

    @Autowired
    @Qualifier("jwtHeaderTokenExtractor")
    lateinit var tokenExtractor: ITokenExtractor

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/create")
    fun createCard(
        request: HttpServletRequest,
        @RequestBody card: CardsDTO): ResponseEntity<Unit> {
        var userCode: UUID? = null

        val tokenPayload = tokenExtractor.Extract(request.getHeader(JwtTokenUtil.TOKEN_HEADER))
        val claims = tokenExtractor.ReadToken(tokenPayload)

        if (!claims.isNullOrEmpty()) {
            if (GenericUtils.getValueFromKeyValue(claims, "userCode") != null) {
                userCode = UUID.fromString(GenericUtils.getValueFromKeyValue(claims, "userCode").toString())
            }
        }
        return ResponseEntity(cardsService.createCard(card, userCode!!), HttpStatus.CREATED)
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping
    fun getCardByUserCode(request: HttpServletRequest): ResponseEntity<List<CardsDTO>> {
            var userCode: UUID? = null

            val tokenPayload = tokenExtractor.Extract(request.getHeader(JwtTokenUtil.TOKEN_HEADER))
            val claims = tokenExtractor.ReadToken(tokenPayload)

            if (!claims.isNullOrEmpty()) {
                if (GenericUtils.getValueFromKeyValue(claims, "userCode") != null) {
                    userCode = UUID.fromString(GenericUtils.getValueFromKeyValue(claims, "userCode").toString())
                }
            }
        return  ResponseEntity(cardsService.getCardByUserCode(userCode!!), HttpStatus.CREATED)

    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping
    fun updateCard(request: HttpServletRequest, @RequestBody updatedCard: CardsDTO): ResponseEntity<CardsDTO> {
        var userCode: UUID? = null

        val tokenPayload = tokenExtractor.Extract(request.getHeader(JwtTokenUtil.TOKEN_HEADER))
        val claims = tokenExtractor.ReadToken(tokenPayload)

        if (!claims.isNullOrEmpty()) {
            if (GenericUtils.getValueFromKeyValue(claims, "userCode") != null) {
                userCode = UUID.fromString(GenericUtils.getValueFromKeyValue(claims, "userCode").toString())
            }
        }
        return  ResponseEntity(cardsService.updateCard(userCode, updatedCard), HttpStatus.CREATED)

    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping
    fun deleteCard(
        request: HttpServletRequest,
        @PathVariable cardNumber: String): ResponseEntity<Unit> {
        var userCode: UUID? = null
        val tokenPayload = tokenExtractor.Extract(request.getHeader(JwtTokenUtil.TOKEN_HEADER))
        val claims = tokenExtractor.ReadToken(tokenPayload)

        if (!claims.isNullOrEmpty()) {
            if (GenericUtils.getValueFromKeyValue(claims, "userCode") != null) {
                userCode = UUID.fromString(GenericUtils.getValueFromKeyValue(claims, "userCode").toString())
            }
        }
        return  ResponseEntity( cardsService.deleteCard(userCode, cardNumber), HttpStatus.OK)
    }
}
