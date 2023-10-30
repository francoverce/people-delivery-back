package com.example.userint.services

import com.example.userint.domain.entities.Trips
import com.example.userint.domain.requests.TripsDTO
import com.example.userint.repositories.sql.TripRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.*

@Service
class TripService  {

    @Autowired
    private lateinit var tripRepository: TripRepository

    @Autowired
    private lateinit var userService: UserService

    enum class Estado {
        SIMULADO,
        EN_PROGRESO,
        CONSULTADO,
        FINALIZADO
    }

    fun step1(trip: TripsDTO, userCode: UUID): Trips {
        userService.getUser(userCode).let {
          return  tripRepository.save(
                Trips(
                    since = trip.since,
                    from = trip.from,
                    distancia = trip.distance,
                    userId = it,
                    created_at = Instant.now(),
                    is_finished = false,
                    status = Estado.SIMULADO.name,
                    driverId = null,
                    paymentType = null,
                )
            )
        }
    }

    fun cancelTrip(code: UUID, status: String, userCode: UUID): Trips? {
      val trip = tripRepository.findByCodeAndUserId_Code(code, userCode)
        if (trip.isPresent) {
            val viaje = trip.get()
            viaje.status = status
            return tripRepository.save(viaje)
        } else {
            throw IllegalArgumentException("El viaje con el code $code no existe.")
        }
    }
}
