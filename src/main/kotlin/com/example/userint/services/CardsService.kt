package com.example.userint.services

import com.example.userint.domain.entities.Cards
import com.example.userint.domain.mappers.CardsMapper.toCardsReponse
import com.example.userint.domain.requests.CardsDTO
import com.example.userint.repositories.sql.CardsRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.*

@Service
class CardsService  {

    @Autowired
    private lateinit var cardsRepository: CardsRepository

    @Autowired
    private lateinit var userService: UserService

    fun createCard(card: CardsDTO, userCode: UUID){
        userService.getUser(userCode).let {
             cardsRepository.save(Cards(
                cardNumber = card.cardNumber,
                cardCVV = card.cardCVV,
                cardExpirationDate = card.cardExpirationDate,
                cardOperator = card.cardOperator,
                userId = it
                )
            )
        }
    }

    fun getCardByUserCode(userCode: UUID): List<CardsDTO> {
        return cardsRepository.findByUserId_Code(userCode).map { it.toCardsReponse() }
    }


    fun updateCard(userCode: UUID?, updatedCard: CardsDTO): CardsDTO {
        val existingCard = cardsRepository.findByUserId_CodeAndCardNumber(userCode!!, updatedCard.cardNumber)

        existingCard.id = existingCard.id
        existingCard.cardNumber = updatedCard.cardNumber
        existingCard.cardOperator = updatedCard.cardOperator
        existingCard.cardExpirationDate = updatedCard.cardExpirationDate
        existingCard.cardCVV = updatedCard.cardCVV

        return cardsRepository.save(existingCard).toCardsReponse()
    }

    fun deleteCard(userCode: UUID?, cardNumber: String) {
        val existingCard = cardsRepository.findByUserId_CodeAndCardNumber(userCode!!,cardNumber)
        cardsRepository.delete(existingCard)
    }
}
