package com.example.userint.client

import com.example.userint.domain.model.EventUserData
import com.example.userint.domain.model.TokenReponse
import com.example.userint.domain.model.TokenRequest
import org.json.JSONObject
import org.springframework.http.*
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class CoreClient {

    var urlJWTBase: String = "https://core-integracion.azurewebsites.net/jwt/token"
    var urlPublishBase: String = "https://core-integracion.azurewebsites.net/api/publish"

    private val restTemplate: RestTemplate = RestTemplate()

    fun sendPostRequest(requestBody: Any): ResponseEntity<String> {
        val headers = HttpHeaders()
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE)
        headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE)
        headers.set("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MTI0OTk3MzU4LCJjb2RlIjoibks7OUJRMil5LUQnMX5ycFNzQmdCZ1ZAQEBUJjRZIn0.k8lmaCgnr3qaeSxl4QqKDaJg8aGLj4ocq9_0B-IP_fo")

        val requestEntity = HttpEntity(requestBody, headers)
        return restTemplate.exchange(urlPublishBase, HttpMethod.POST, requestEntity, String::class.java)
    }

    fun sendGetRequest(requestBody: TokenRequest): ResponseEntity<TokenReponse> {
        val headers = HttpHeaders()
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE)
        headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE)
        val requestEntity = HttpEntity(requestBody,headers)
        return restTemplate.exchange(urlJWTBase, HttpMethod.GET, requestEntity, TokenReponse::class.java)
    }
}


