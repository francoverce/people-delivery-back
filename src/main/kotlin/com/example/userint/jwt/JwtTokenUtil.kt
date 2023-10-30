package com.example.userint.jwt

import io.jsonwebtoken.*
import org.slf4j.LoggerFactory
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.io.Serializable
import java.util.*
import java.util.function.Function

@Component
class JwtTokenUtil : Serializable {
    fun getUsernameFromToken(token: String?): String {
        return getClaimFromToken(token) { obj: Claims -> obj.subject }
    }

    fun getIssuedAtDateFromToken(token: String?): Date {
        return getClaimFromToken(token) { obj: Claims -> obj.issuedAt }
    }

    fun getExpirationDateFromToken(token: String?): Date {
        return getClaimFromToken(token) { obj: Claims -> obj.expiration }
    }

    fun <T> getClaimFromToken(token: String?, claimsResolver: Function<Claims, T>): T {
        val claims = getAllClaimsFromToken(token)
        return claimsResolver.apply(claims)
    }

    private fun getAllClaimsFromToken(token: String?): Claims {
        // return Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token).getBody();
        return Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token).body
    }

    fun getMapFromTokenClaims(token: String?): Map<String?, Any?> {
        val expectedMap: MutableMap<String?, Any?> = HashMap()
        if (tryParseJwtToken(token)) {
            val claims = getAllClaimsFromToken(token)
            for ((key, value) in claims) {
                expectedMap[key] = value
            }
        }
        return expectedMap
    }

    fun isTokenSigned(token: String?): Boolean {
        return Jwts.parser().isSigned(token)
    }

    fun isTokenExpired(token: String?): Boolean {
        val expiration = getExpirationDateFromToken(token)
        return expiration.before(Date())
    }

    fun generateToken(userDetails: UserDetails?, claims: Map<String, Any?>): String {
        return doGenerateToken(claims, userDetails!!.username)
    }

    private fun doGenerateToken(claims: Map<String, Any?>, subject: String): String {
        val key = JWT_SECRET // JWT_SECRET;
        return Jwts.builder()
            .setId(UUID.randomUUID().toString())
            .setClaims(claims)
            .setSubject(subject)
            .setHeaderParam("typ", TOKEN_TYPE)
            .setIssuer(TOKEN_ISSUER)
            .setAudience(TOKEN_AUDIENCE)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setNotBefore(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 120000))
            .signWith(SignatureAlgorithm.HS512, key)
            .compact()
    }

    fun validateToken(token: String?, userDetails: UserDetails?): Boolean {
        val username = getUsernameFromToken(token)
        return username == userDetails!!.username && !isTokenExpired(token) && isTokenSigned(token)
    }

    fun tryParseJwtToken(authToken: String?): Boolean {
        try {
            val key = JWT_SECRET // JWT_SECRET;
            Jwts.parser().setSigningKey(key).parseClaimsJws(authToken)
            return true
        } catch (e: SignatureException) {
            logger.error("Invalid JWT signature: {}", e.message)
        } catch (e: MalformedJwtException) {
            logger.error("Invalid JWT token: {}", e.message)
        } catch (e: ExpiredJwtException) {
            logger.error("JWT token is expired: {}", e.message)
        } catch (e: UnsupportedJwtException) {
            logger.error("JWT token is unsupported: {}", e.message)
        } catch (e: IllegalArgumentException) {
            logger.error("JWT claims string is empty: {}", e.message)
        }
        return false
    }

    companion object {
        private val logger = LoggerFactory.getLogger(JwtTokenUtil::class.java)
        private const val serialVersionUID = -2550185165626007488L
        const val JWT_TOKEN_VALIDITY = (5 * 60 * 60).toLong()

        // Signing key for HS512 algorithm
        const val JWT_SECRET = "e5c9ee274ae87bc031adda32e27fa98b9290da90"

        // JWT token defaults
        const val TOKEN_TYPE = "JWT"
        const val TOKEN_ISSUER = "secure-api"
        const val TOKEN_AUDIENCE = "secure-app"
        const val TOKEN_HEADER = "Authorization"
        const val TOKEN_PREFIX = "Bearer "
    }
}
