package com.example.userint.v1

import com.example.userint.domain.entities.Reviews
import com.example.userint.domain.requests.CreateReviewsRequest
import com.example.userint.jwt.ITokenExtractor
import com.example.userint.jwt.JwtTokenUtil
import com.example.userint.services.ReviewsService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import model.GenericUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.servlet.http.HttpServletRequest

@RestController
@Validated
@CrossOrigin
@Api(value = "Reviews de choferes", tags = ["Reviews de choferes"], protocols = "https")
@RequestMapping(value = ["v1/reviews"])
class JwtReviewsControllerV1 {

    @Autowired
    private lateinit var reviewsService: ReviewsService

    @Autowired
    @Qualifier("jwtHeaderTokenExtractor")
    lateinit var tokenExtractor: ITokenExtractor

    @CrossOrigin
    @PostMapping
    @ApiOperation(value = "Crea una review de un chofer por usuario")
    fun createReview(
        request: HttpServletRequest,
        @RequestBody(required = true) createReviewsRequest: CreateReviewsRequest
    ): ResponseEntity<Reviews> {
        var userCode: UUID? = null

        val tokenPayload = tokenExtractor.Extract(request.getHeader(JwtTokenUtil.TOKEN_HEADER))
        val claims = tokenExtractor.ReadToken(tokenPayload)

        if (!claims.isNullOrEmpty()) {
            if (GenericUtils.getValueFromKeyValue(claims, "userCode") != null) {
                userCode = UUID.fromString(GenericUtils.getValueFromKeyValue(claims, "userCode").toString())
            }
        }
        return ResponseEntity(reviewsService.createReview(userCode!!, createReviewsRequest), HttpStatus.OK)
    }

    @CrossOrigin
    @GetMapping
    @ApiOperation(value = "Crea una review de un chofer por usuario")
    fun getReviewByOrgCode(
        request: HttpServletRequest,
        @RequestHeader(value = "x-chofer-code", required = true) restaurantCode: UUID,
    ) = reviewsService.getReviewsByRestaurantCode(restaurantCode)
}
