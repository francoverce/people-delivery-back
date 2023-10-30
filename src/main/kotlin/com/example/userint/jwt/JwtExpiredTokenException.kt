package com.example.userint.jwt

import org.springframework.security.core.AuthenticationException

class JwtExpiredTokenException : AuthenticationException {
    constructor(msg: String?) : super(msg) {}
    constructor(msg: String?, t: Throwable?) : super(msg, t) {}

    companion object {
        private const val serialVersionUID = -5959543783324224864L
    }
}
