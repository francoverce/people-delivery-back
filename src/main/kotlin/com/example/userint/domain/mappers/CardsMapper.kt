package com.example.userint.domain.mappers

import com.example.userint.domain.entities.Cards
import com.example.userint.domain.entities.Users
import com.example.userint.domain.requests.CardsDTO
import com.example.userint.domain.responses.UsersResponse

object CardsMapper {
    fun Cards.toCardsReponse(): CardsDTO {
        return CardsDTO(
        cardNumber = cardNumber,
        cardOperator = cardOperator,
        cardExpirationDate= cardExpirationDate,
        cardCVV= cardCVV,
        )
    }
}
