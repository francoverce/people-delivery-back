package com.example.userint.jwt

import com.example.userint.services.JwtUserDetailsService
import io.jsonwebtoken.ExpiredJwtException
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtRequestFilter : OncePerRequestFilter() {

    @Autowired
    lateinit var jwtUserDetailsService: JwtUserDetailsService

    @Autowired
    lateinit var jwtTokenUtil: JwtTokenUtil

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val requestTokenHeader = request.getHeader(JwtTokenUtil.TOKEN_HEADER)
        var username: String? = null
        var jwtToken: String? = null
        // JWT Token is in the form "Bearer token". Remove Bearer word and get only the Token
        if (requestTokenHeader != null &&
            requestTokenHeader.startsWith(JwtTokenUtil.TOKEN_PREFIX)
        ) {
            jwtToken = requestTokenHeader.substring(7)
            try {
                if (jwtTokenUtil!!.tryParseJwtToken(jwtToken)) {
                    username = jwtTokenUtil.getUsernameFromToken(jwtToken)
                } else {
                    Companion.logger.warn("Invalid JWT Token")
                }
            } catch (e: IllegalArgumentException) {
                Companion.logger.error("Unable to get JWT Token", e)
            } catch (e: ExpiredJwtException) {
                Companion.logger.error("JWT Token has expired", e)
            }
        } else {
            logger.warn("JWT Token does not begin with Bearer String")
        }

        // Once we get the token validate it.
        if (username != null &&
            SecurityContextHolder.getContext().authentication == null
        ) {
            val userDetails = jwtUserDetailsService.loadUserByUsername(username)
            // if token is valid configure Spring Security to manually set authentication
            if (jwtTokenUtil!!.validateToken(jwtToken, userDetails)) {
                val usernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails!!.authorities
                )
                usernamePasswordAuthenticationToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                // After setting the Authentication in the context, we specify
                // that the current user is authenticated. So it passes the Spring Security Configurations successfully.
                SecurityContextHolder.getContext().authentication = usernamePasswordAuthenticationToken
            }
        }
        chain.doFilter(request, response)
    }

    companion object {
        private val logger = LoggerFactory.getLogger(JwtRequestFilter::class.java)
    }
}
