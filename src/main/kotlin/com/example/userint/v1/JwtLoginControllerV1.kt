package com.example.userint.v1

import com.example.userint.domain.responses.RegisterResponse
import com.example.userint.jwt.JwtRequestStep2
import com.example.userint.services.LoginService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.servlet.http.HttpServletRequest

@RestController
@Validated
@CrossOrigin
@Api(value = "Register", tags = ["Autenticacion de usuarios registrados"], protocols = "https")
@RequestMapping(value = ["v1/login"])
class JwtLoginControllerV1 {

    @Autowired
    private lateinit var loginService: LoginService

    @CrossOrigin
    @PostMapping("/authenticate")
    @ApiOperation(value = "Autentifica un usuario previamente registrado devolviendo sus datos y jwt")
    fun Authenticate(
        request: HttpServletRequest,
        @RequestBody(required = true)
        @Validated authenticationRequest: JwtRequestStep2
    ): ResponseEntity<RegisterResponse?> = ResponseEntity(loginService.authenticate(authenticationRequest.email!!, authenticationRequest.password!!), HttpStatus.OK)
}
