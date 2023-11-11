package com.example.userint.services

import com.example.userint.client.CoreClient
import com.example.userint.config.ConfigCommon
import com.example.userint.domain.entities.Users
import com.example.userint.domain.model.EventUpdateUserData
import com.example.userint.domain.model.MessageUpdateUser
import com.example.userint.domain.requests.PatchUserRequest
import com.example.userint.domain.responses.OTPResponse
import com.example.userint.repositories.clients.SengridApiClient
import com.example.userint.repositories.sql.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.util.*

@Service("UserService")
class UserService {

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var sengridApiClient: SengridApiClient

    @Autowired
    lateinit var configCommon: ConfigCommon

    @Autowired
    lateinit var coreClient: CoreClient


    @Transactional
    fun pathUser(userCode: UUID, patchUserRequest: PatchUserRequest): Users {
        userRepository.findByCode(userCode).let {
            val user = userRepository.save(
                Users(
                    id = it.id,
                    code = it.code,
                    userName = patchUserRequest.userName ?: it.userName,
                    phone = patchUserRequest.phone ?: it.phone,
                    name = patchUserRequest.name ?: it.name,
                    password = it.password,
                    email = it.email,
                    rol = it.rol,
                    lastName = patchUserRequest.lastName ?: it.lastName,
                    adress = patchUserRequest.adress ?: it.adress,
                    favorites = it.favorites,
                    icon = it.icon
                )
            )

            coreClient.sendPostRequest(
                EventUpdateUserData(
                    exchange = "update_user",
                    message = MessageUpdateUser(
                        id = user.id,
                        nombre = user.name,
                        apellido = user.lastName ?: "",
                        usuario = user.userName,
                        email = user.email,
                        telefono = user.phone,
                        direccion = user.adress ?: "",
                    )
                )
            )

            return user
        }
    }

    @Transactional
    fun getUser(userCode: UUID) =
        userRepository.findByCode(userCode)

    @Transactional
    fun deleteUser(userCode: UUID) = userRepository.deleteByCode(userCode)

    @Transactional
    fun getOTPEmail(email: String): OTPResponse {

        userRepository.findByEmail(email).let {
            val infoOTP = userRepository.save(
                Users(
                    id = it.id,
                    code = it.code,
                    email = it.email,
                    password = it.password,
                    otpCode = String.format("%04d", Random().nextInt(10000)),
                    dateValidationOTP = Instant.now(),
                    favorites = it.favorites,
                    icon = it.icon,
                    adress = it.adress,
                    rol = it.rol
                )
            )

            sengridApiClient.postOTPEmail(email, infoOTP.otpCode!!)

            return OTPResponse(
                code = "200",
                message = "Se envio el codigo otp al email :$email"
            )
        }
    }
    @Transactional
    fun getValidateOTPByEmailAndCode(email: String, otpCode: String): OTPResponse {
        userRepository.findByEmail(email).let {
            if (it.otpCode == otpCode) {

                val calendar_ = Calendar.getInstance()
                val calendar = Calendar.getInstance()

                calendar.time = Date() // Configuramos la fecha que se recibe
                calendar_.add(Calendar.MINUTE, 3) // Sumo 3 minutos a la fecha de creacion

                if (calendar_.after(calendar)) {

                    val newPass = UUID.randomUUID().toString().replace("-", "").substring(0, 8)

                    userRepository.save(
                        Users(
                            id = it.id,
                            password = configCommon.hashWith256(newPass.uppercase())!!,
                            email = it.email,
                            code = it.code,
                            updated_at = Instant.now(),
                            favorites = it.favorites,
                            rol = it.rol,
                            adress = it.adress,
                            icon = it.icon
                        )
                    )

                    sengridApiClient.postOTPEmailSucces(email, newPass.uppercase())

                    return OTPResponse(
                        code = "200",
                        message = "Se envio la nueva contraseña al email: $email"
                    )
                } else {
                    return OTPResponse(
                        code = "404",
                        message = "Codigo OTP expirado"
                    )
                }
            } else {
                return OTPResponse(
                    code = "404",
                    message = "Codigo OTP erroneo"
                )
            }
        }
    }
}
