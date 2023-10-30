package com.example.userint.services

import com.example.userint.config.ConfigCommon
import com.example.userint.domain.responses.RegisterResponse
import com.example.userint.exceptions.UserNotFoundException
import com.example.userint.exceptions.UserPasswordWrong
import com.example.userint.jwt.JwtTokenUtil
import com.example.userint.repositories.sql.RegisterRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class LoginService {

    @Autowired
    private lateinit var registerRepository: RegisterRepository

    @Autowired
    private lateinit var configCommon: ConfigCommon

    @Autowired
    private lateinit var jwtTokenUtil: JwtTokenUtil

    @Autowired
    private lateinit var jwtInMemoryUserDetailsService: UserDetailsService

    fun authenticate(email: String, password: String): RegisterResponse {
        registerRepository.findByEmail(email)?.let {

            if (it.password != configCommon.hashWith256(password)) {
                throw UserPasswordWrong("PASSWORD_INVALID")
            }

            var userDetails: UserDetails? = null
            val claims: MutableMap<String, Any?> = HashMap()

            try {
                userDetails = jwtInMemoryUserDetailsService.loadUserByUsername(email)
            } catch (e: Exception) {
                println(e.message)
            }
            claims["email"] = it.email
            claims["userId"] = it.id
            claims["nombre"] = it.name
            claims["userCode"] = it.code

            val token = jwtTokenUtil.generateToken(userDetails, claims)

            return RegisterResponse(
                code = "200",
                message = "Se logeo correctamente",
                sessionEnc = token,
                userCaseId = "usuario_loggeado_correctamente"
            )
        } ?: throw UserNotFoundException("USER_NOT_FOUND")
    }
}
