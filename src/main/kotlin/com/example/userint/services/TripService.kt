package com.example.userint.services

import com.example.userint.client.CoreClient
import com.example.userint.domain.entities.Drivers
import com.example.userint.domain.entities.Trips
import com.example.userint.domain.model.*
import com.example.userint.domain.requests.AcceptedTrip
import com.example.userint.domain.requests.ClosedTrip
import com.example.userint.domain.requests.OnGoingTrip
import com.example.userint.domain.requests.TripsDTO
import com.example.userint.repositories.sql.DriverRepository
import com.example.userint.repositories.sql.TripRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.simp.SimpMessageSendingOperations
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.util.*

@Service
class TripService {

    @Autowired
    private lateinit var tripRepository: TripRepository

    @Autowired
    private lateinit var driverRepository: DriverRepository

    @Autowired
    private lateinit var userService: UserService

    @Autowired
    lateinit var coreClient: CoreClient

    @Autowired
    lateinit var messagingTemplate: SimpMessageSendingOperations

    enum class Estado {
        SIMULADO,
        CANCELLED_BEFORE_REQUESTING_DRIVER,
        CANCELLED_AFTER_REQUESTING_DRIVER,
        EN_CAMINO,
        INICIADO,
        FINALIZADO,
    }

    @Transactional
    fun step1(trip: TripsDTO, userCode: UUID): Trips {
        userService.getUser(userCode).let {
            val trip = tripRepository.save(
                Trips(
                    since = trip.since,
                    from = trip.from,
                    distancia = trip.distance,
                    userId = it,
                    created_at = Instant.now(),
                    is_finished = false,
                    status = Estado.SIMULADO.name,
                    driverId = null,
                    paymentType = trip.paymentMethod ?: "not-defined",
                    precio = trip.distance * 300F,
                )
            )
            coreClient.sendPostRequest(
                EventTrip(
                    exchange = "new_trips",
                    message = MessageNewTrip(
                        idViaje = it.id,
                        nombre = it.name ?: "",
                        apellido = it.lastName ?: "",
                        date = trip.created_at,
                        estadoViaje = Estado.SIMULADO.name,
                        puntoPartida = trip.from!!,
                        puntoLlegada = trip.since,
                        isMovilidadReducida = trip.isMobilityReduce,
                        metodoPago = trip.paymentType!!,
                        precio = trip.distancia!! * 300F,
                        distancia = trip.distancia!!
                    )
                )
            )
            return trip
        }
    }

    @Transactional
    fun cancelTrip(code: UUID, status: String, userCode: UUID): Trips? {
        val trip = tripRepository.findByCodeAndUserId_Code(code, userCode)
        if (trip.isPresent) {
            val viaje = trip.get()
            viaje.status = status
            viaje.updated_at = Instant.now()
            if (status == (Estado.CANCELLED_BEFORE_REQUESTING_DRIVER.name)) {
                coreClient.sendPostRequest(
                    EventTripCancelledTripsBeforeRequestingDriver(
                        exchange = "cancelled_trips_before_requesting_driver",
                        message = MessageTripCancelledTripsBeforeRequestingDriver(
                            idViaje = trip.get().id,
                            nombre = trip.get().userId.name!!,
                            apellido = trip.get().userId.lastName!!,
                            date = trip.get().created_at,
                            estadoViaje = Estado.CANCELLED_BEFORE_REQUESTING_DRIVER.name,
                            puntoPartida = trip.get().from!!,
                            puntoLlegada = trip.get().since,
                            isMovilidadReducida = trip.get().isMobilityReduce,
                            metodoPago = trip.get().paymentType!!,
                            precio = trip.get().precio!!,
                            distancia = trip.get().distancia!!
                        )
                    )
                )
            }
            if (status == (Estado.CANCELLED_AFTER_REQUESTING_DRIVER.name)) {
                coreClient.sendPostRequest(
                    EventTripCancelledTripsAfterRequestingDriver(
                        exchange = "cancelled_trips",
                        message = MessageTripCancelledTripsAfterRequestingDriver(
                            idViaje = trip.get().id,
                            date = trip.get().created_at,
                            estadoViaje = Estado.CANCELLED_AFTER_REQUESTING_DRIVER.name,
                        )
                    )
                )
            }
            return tripRepository.save(viaje)
        } else {
            throw IllegalArgumentException("El viaje con el code $code no existe.")
        }
    }

    @Transactional
    fun acceptedTrip(event: AcceptedTrip) {
       val driver =  driverRepository.save(Drivers(
            idChofer = event.idChofer,
            fullName = event.nombreChofer + " " + event.apellidoChofer,
            car = event.vehiculo,
            patent = event.patente,
            dateCome = event.date,
            icon = ""
        ))

        tripRepository.findById(event.idViaje).let {
            tripRepository.save(
                Trips(
                    id = it.get().id,
                    since = it.get().since,
                    from = it.get().from,
                    distancia = it.get().distancia,
                    userId = it.get().userId,
                    created_at = it.get().created_at,
                    updated_at = Instant.now(),
                    is_finished = it.get().is_finished,
                    status = Estado.EN_CAMINO.name,
                    driverId = driver,
                    paymentType = it.get().paymentType,
                    precio = it.get().precio,
                )
            )
        }
        val novedad = "El viaje con ID ${event.idViaje} ha sido aceptado"
        messagingTemplate.convertAndSend("/topic/novedad", novedad)
    }



    @Transactional
    fun ongoinTrip(event: OnGoingTrip) {
        tripRepository.findById(event.idViaje).let {
            tripRepository.save(
                Trips(
                    id = it.get().id,
                    since = it.get().since,
                    from = it.get().from,
                    distancia = it.get().distancia,
                    userId = it.get().userId,
                    updated_at = Instant.now(),
                    is_finished = it.get().is_finished,
                    status = Estado.INICIADO.name,
                    driverId = it.get().driverId,
                    paymentType = it.get().paymentType,
                    precio = it.get().precio,
                )
            )
        }
        val novedad = "El viaje con ID ${event.idViaje} ha sido inciado"
        messagingTemplate.convertAndSend("/topic/novedad", novedad)
    }


    @Transactional
    fun closedTrip(event: ClosedTrip) {
        tripRepository.findById(event.idViaje).let {
            tripRepository.save(
                Trips(
                    id = it.get().id,
                    since = it.get().since,
                    from = it.get().from,
                    distancia = it.get().distancia,
                    userId = it.get().userId,
                    updated_at = Instant.now(),
                    is_finished = true,
                    status = event.status,
                    driverId = it.get().driverId,
                    paymentType = it.get().paymentType,
                    precio = it.get().precio,
                )
            )
        }
        val novedad = "El viaje con ID ${event.idViaje} ha sido finalizado"
        messagingTemplate.convertAndSend("/topic/novedad", novedad)
    }
}
