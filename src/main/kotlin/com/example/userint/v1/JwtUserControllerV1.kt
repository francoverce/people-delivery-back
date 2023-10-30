package com.example.userint.v1

import com.example.userint.domain.mappers.UsersMapper.toUserReponse
import com.example.userint.domain.requests.PatchUserRequest
import com.example.userint.domain.responses.UsersResponse
import com.example.userint.jwt.ITokenExtractor
import com.example.userint.jwt.JwtTokenUtil
import com.example.userint.services.UserService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import model.GenericUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.servlet.http.HttpServletRequest

@RestController
@CrossOrigin
@Api(value = "", tags = ["Usuarios"], protocols = "https")
@RequestMapping(value = ["v1/users"])
class JwtUserControllerV1 {

    @Autowired
    private lateinit var userService: UserService

    @Autowired
    @Qualifier("jwtHeaderTokenExtractor")
    lateinit var tokenExtractor: ITokenExtractor

    @PreAuthorize("hasRole('USER')")
    @ApiOperation(value = "Recurso para obtener los datos de un usuario")
    @GetMapping
    @ResponseBody
    fun getUser(
        request: HttpServletRequest,
    ): ResponseEntity<UsersResponse> {
        var userCode: UUID? = null

        val tokenPayload = tokenExtractor.Extract(request.getHeader(JwtTokenUtil.TOKEN_HEADER))
        val claims = tokenExtractor.ReadToken(tokenPayload)

        if (!claims.isNullOrEmpty()) {
            if (GenericUtils.getValueFromKeyValue(claims, "userCode") != null) {
                userCode = UUID.fromString(GenericUtils.getValueFromKeyValue(claims, "userCode").toString())
            }
        }
        return ResponseEntity(
            userService.getUser(userCode!!).toUserReponse(), HttpStatus.OK
        )
    }

    @PreAuthorize("hasRole('USER')")
    @ApiOperation(value = "Recurso para cambiar los datos de un usuario")
    @PatchMapping
    @ResponseBody
    fun pathUser(
        request: HttpServletRequest,
        @RequestBody(required = true) patchUserRequest: PatchUserRequest
    ): ResponseEntity<UsersResponse> {
        var userCode: UUID? = null

        val tokenPayload = tokenExtractor.Extract(request.getHeader(JwtTokenUtil.TOKEN_HEADER))
        val claims = tokenExtractor.ReadToken(tokenPayload)

        if (!claims.isNullOrEmpty()) {
            if (GenericUtils.getValueFromKeyValue(claims, "userCode") != null) {
                userCode = UUID.fromString(GenericUtils.getValueFromKeyValue(claims, "userCode").toString())
            }
        }
        return ResponseEntity(
            userService.pathUser(userCode!!, patchUserRequest).toUserReponse(), HttpStatus.CREATED
        )
    }

    @PreAuthorize("hasRole('USER')")
    @ApiOperation(value = "Eliminar usuario")
    @DeleteMapping
    @ResponseBody
    fun deleteUser(
        request: HttpServletRequest,
    ): ResponseEntity<Any> {
        var userCode: UUID? = null

        val tokenPayload = tokenExtractor.Extract(request.getHeader(JwtTokenUtil.TOKEN_HEADER))
        val claims = tokenExtractor.ReadToken(tokenPayload)

        if (!claims.isNullOrEmpty()) {
            if (GenericUtils.getValueFromKeyValue(claims, "userCode") != null) {
                userCode = UUID.fromString(GenericUtils.getValueFromKeyValue(claims, "userCode").toString())
            }
        }
        return ResponseEntity(
            userService.deleteUser(userCode!!), HttpStatus.CREATED
        )
    }

    @GetMapping(value = ["/email/{email}/otp"])
    @ApiOperation(value = "Solicita el codigo otp para recuperacion de usuario")
    @ResponseBody
    fun getOTPByEmail(
        request: HttpServletRequest?,
        @PathVariable("email") email: String
    ) = userService.getOTPEmail(email)

    @GetMapping(value = ["/validate/email/{email}/otp/{otpCode}"])
    @ApiOperation(value = "Valida y cambia la password del usuario")
    @ResponseBody
    fun getValidateOTPByEmailAndCode(
        request: HttpServletRequest?,
        @PathVariable("email") email: String,
        @PathVariable("otpCode") otpCode: String
    ) =
        userService.getValidateOTPByEmailAndCode(email, otpCode)

}
