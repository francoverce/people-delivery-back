package com.example.userint.services

import com.example.userint.client.CoreClient
import com.example.userint.domain.entities.Cards
import com.example.userint.domain.entities.Users
import com.example.userint.domain.mappers.CardsMapper.toCardsReponse
import com.example.userint.domain.model.EventUserData
import com.example.userint.domain.model.Message
import com.example.userint.domain.requests.CardsDTO
import com.example.userint.repositories.sql.CardsRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.util.*
import javax.persistence.RollbackException

@Service
class CardsService  {

    @Autowired
    private lateinit var cardsRepository: CardsRepository

    @Autowired
    private lateinit var userService: UserService

    @Autowired
    lateinit var coreClient: CoreClient

    @Transactional
    fun createCard(card: CardsDTO, userCode: UUID) {
        val user = userService.getUser(userCode)

        val savedCard = saveCardForUser(card, user)
        val eventUserData = createEventUserData(user, savedCard)

        coreClient.sendPostRequest(eventUserData)
    }

    private fun saveCardForUser(card: CardsDTO, user: Users): Cards {
        return cardsRepository.save(
            Cards(
                cardNumber = card.cardNumber,
                cardCVV = card.cardCVV,
                cardExpirationDate = card.cardExpirationDate,
                cardOperator = card.cardOperator,
                userId = user
            )
        )
    }

    private fun createEventUserData(user: Users, card: Cards): EventUserData {
        return EventUserData(
            exchange = "update_user",
            message = Message(
                idUsuario = user.id.toString(),
                nombreUsuario = "${user.name} ${user.lastName}",
                correoElectronico = user.email,
                numeroDeTelefono = user.phone,
                direccionDefecto = user.adress,
                operadoraTarjeta = card.cardOperator,
                nroTarjeta = card.cardNumber,
                vencimientoTarjeta = card.cardExpirationDate,
                codigoSeguridadTarjeta = card.cardCVV
            )
        )
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

        val eventUserData = createEventUserData(existingCard.userId, existingCard)
        coreClient.sendPostRequest(eventUserData)

        return cardsRepository.save(existingCard).toCardsReponse()
    }

    fun deleteCard(userCode: UUID?, cardNumber: String) {
        val existingCard = cardsRepository.findByUserId_CodeAndCardNumber(userCode!!,cardNumber)
        cardsRepository.delete(existingCard)
    }
}
