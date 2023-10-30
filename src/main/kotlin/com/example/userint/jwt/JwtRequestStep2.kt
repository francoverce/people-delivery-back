package com.example.userint.jwt

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.annotations.ApiModelProperty
import java.io.Serializable

class JwtRequestStep2 @JsonCreator constructor(
    @JsonProperty("email") email: String?,
    @JsonProperty("password") password: String?
) : Serializable {
    @get:JsonProperty(
        value = "email",
        access = JsonProperty.Access.READ_ONLY
    )
    @set:JsonProperty(
        value = "email",
        access = JsonProperty.Access.WRITE_ONLY
    )
    @ApiModelProperty(required = true, value = "String correspondiente al email del usuario")
    var email: String? = null

    @get:JsonProperty(
        value = "password",
        access = JsonProperty.Access.READ_ONLY
    )
    @set:JsonProperty(
        value = "password",
        access = JsonProperty.Access.WRITE_ONLY
    )
    @ApiModelProperty(required = true, value = "String correspondiente a la password del usuario")
    var password: String? = null

    init {
        this.email = email
        this.password = password
    }

    companion object {
        private const val serialVersionUID = 5926468583005150707L
    }
}
