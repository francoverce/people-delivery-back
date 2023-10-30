package com.example.userint.jwt

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.annotations.ApiModelProperty
import java.io.Serializable

class JwtRequestStep1 @JsonCreator constructor(
    @JsonProperty("username") username: String?,
    @JsonProperty("name") name: String?,
    @JsonProperty("lastName") lastName: String?,
    @JsonProperty("phone") phone: String?,
    @JsonProperty("password") password: String?,
    @JsonProperty("email") email: String?
) : Serializable {
    @get:JsonProperty(
        value = "username",
        access = JsonProperty.Access.READ_ONLY
    )
    @set:JsonProperty(
        value = "username",
        access = JsonProperty.Access.WRITE_ONLY
    )
    @ApiModelProperty(required = true, value = "String correspondiente al username del usuario")
    var username: String? = null

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

    @get:JsonProperty(
        value = "email",
        access = JsonProperty.Access.READ_ONLY
    )
    @set:JsonProperty(
        value = "email",
        access = JsonProperty.Access.WRITE_ONLY
    )
    @ApiModelProperty(required = true, value = "String correspondiente a la email del usuario")
    var email: String? = null

    @get:JsonProperty(
        value = "name",
        access = JsonProperty.Access.READ_ONLY
    )
    @set:JsonProperty(
        value = "name",
        access = JsonProperty.Access.WRITE_ONLY
    )
    @ApiModelProperty(required = true, value = "String correspondiente a la name del usuario")
    var name: String? = null

    @get:JsonProperty(
        value = "lastName",
        access = JsonProperty.Access.READ_ONLY
    )
    @set:JsonProperty(
        value = "lastName",
        access = JsonProperty.Access.WRITE_ONLY
    )
    @ApiModelProperty(required = true, value = "String correspondiente a la lastName del usuario")
    var lastName: String? = null


    @get:JsonProperty(
        value = "phone",
        access = JsonProperty.Access.READ_ONLY
    )
    @set:JsonProperty(
        value = "phone",
        access = JsonProperty.Access.WRITE_ONLY
    )
    @ApiModelProperty(required = true, value = "String correspondiente a la phone del usuario")
    var phone: String? = null


    init {
        this.username = username
        this.password = password
        this.email = email
        this.name = name
        this.lastName = lastName
        this.phone = phone
    }

    companion object {
        private const val serialVersionUID = 5926468583005150707L
    }
}
