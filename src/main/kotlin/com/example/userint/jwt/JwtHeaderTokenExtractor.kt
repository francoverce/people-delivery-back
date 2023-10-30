package com.example.userint.jwt

import com.vladmihalcea.hibernate.util.StringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationServiceException
import org.springframework.stereotype.Component

@Component
class JwtHeaderTokenExtractor : ITokenExtractor {

    @Autowired
    lateinit var jwtTokenUtil: JwtTokenUtil

    override fun Extract(header: String): String {
        if (StringUtils.isBlank(header)) {
            throw AuthenticationServiceException("Authorization header cannot be blank!")
        }
        if (header.length < HEADER_PREFIX.length) {
            throw AuthenticationServiceException("Invalid authorization header size.")
        }
        return header.substring(HEADER_PREFIX.length, header.length)
    }

    @Throws(Exception::class)
    override fun ReadToken(token: String?): Map<String?, Any?>? {
        if (jwtTokenUtil.isTokenExpired(token)) {
            throw JwtExpiredTokenException(token)
        }
        var claims = jwtTokenUtil.getMapFromTokenClaims(token)

        return claims
    }

    companion object {
        var HEADER_PREFIX = "Bearer "
    }
}
