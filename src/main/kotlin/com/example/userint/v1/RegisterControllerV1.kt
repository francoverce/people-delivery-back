package com.example.userint.v1

import com.example.userint.domain.responses.RegisterResponse
import com.example.userint.jwt.JwtRequestStep1
import com.example.userint.services.RegisterService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.servlet.http.HttpServletRequest

@CrossOrigin
@RestController
@RequestMapping(value = ["v1/register"])
@Validated
@Api(value = "", tags = ["Registro de datos personales de los usuarios"], protocols = "https")
class RegisterControllerV1 {

    @Autowired
    lateinit var registerService: RegisterService

    @PostMapping
    @ApiOperation(value = "Recurso para escribir todos los datos personales. Retorno status 200 y json en Body response.")
    @ResponseBody
    fun register(
        request: HttpServletRequest,
        @RequestBody(required = true)
        @Validated jwtRequestStep1: JwtRequestStep1
    ): ResponseEntity<RegisterResponse?> {

        Objects.requireNonNull(jwtRequestStep1.password)
        Objects.requireNonNull(jwtRequestStep1.email)
        Objects.requireNonNull(jwtRequestStep1.username)
        Objects.requireNonNull(jwtRequestStep1.name)
        Objects.requireNonNull(jwtRequestStep1.lastName)
        Objects.requireNonNull(jwtRequestStep1.phone)


        return ResponseEntity(registerService.Register(jwtRequestStep1), HttpStatus.OK)
    }
}
