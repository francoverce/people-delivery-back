package com.example.userint.jwt

interface ITokenExtractor {

    fun Extract(payload: String): String

    fun ReadToken(token: String?): Map<String?, Any?>?
}
