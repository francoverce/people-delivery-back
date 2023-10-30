package com.example.userint.domain.mappers

import com.example.userint.domain.entities.Users
import com.example.userint.domain.responses.UsersResponse

object UsersMapper {
    fun Users.toUserReponse(): UsersResponse {
        return UsersResponse(
            name = name,
            userName = userName,
            lastName = lastName,
            phone = phone,
            adress = adress,
            email = email,
        )
    }
}
