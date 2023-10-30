package com.example.userint.jwt

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.annotations.ApiModelProperty
import java.io.Serializable

class JwtRequesForgetPassword @JsonCreator constructor(
    @JsonProperty("olderPassword") olderPassword: String?,
    @JsonProperty("newPassword") newPassword: String?
) : Serializable {
    @get:JsonProperty(
        value = "olderPassword",
        access = JsonProperty.Access.READ_ONLY
    )
    @set:JsonProperty(
        value = "olderPassword",
        access = JsonProperty.Access.WRITE_ONLY
    )
    @ApiModelProperty(required = true, value = "Older Password")
    var olderPassword: String? = null

    @get:JsonProperty(
        value = "newPassword",
        access = JsonProperty.Access.READ_ONLY
    )
    @set:JsonProperty(
        value = "password",
        access = JsonProperty.Access.WRITE_ONLY
    )
    @ApiModelProperty(required = true, value = "New Password")
    var newPassword: String? = null

    init {
        this.olderPassword = olderPassword
        this.newPassword = newPassword
    }

    companion object {
        private const val serialVersionUID = 5926468583005150707L
    }
}
