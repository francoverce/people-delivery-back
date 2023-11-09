package com.example.userint.services

import com.example.userint.client.CoreClient
import com.example.userint.config.ConfigCommon
import com.example.userint.domain.entities.Users
import com.example.userint.domain.model.EventUserData
import com.example.userint.domain.model.Message
import com.example.userint.domain.responses.RegisterResponse
import com.example.userint.exceptions.UserRegistedException
import com.example.userint.jwt.JwtRequestStep1
import com.example.userint.repositories.sql.RegisterRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service("RegisterService")
class RegisterService {

    @Autowired
    lateinit var registerRepository: RegisterRepository

    @Autowired
    lateinit var configCommon: ConfigCommon

    @Autowired
    lateinit var coreClient: CoreClient

    fun Register(jwtRequestStep1: JwtRequestStep1): RegisterResponse {
        if (registerRepository.findByEmail(jwtRequestStep1.email!!) == null) {
          val newUser = registerRepository.save(
                Users(
                    password = configCommon.hashWith256(jwtRequestStep1.password).toString(),
                    userName = jwtRequestStep1.username,
                    name = jwtRequestStep1.name,
                    phone = jwtRequestStep1.phone,
                    email = jwtRequestStep1.email!!,
                    lastName = jwtRequestStep1.lastName,
                    rol = "ADMIN",
                    icon = "",
                    favorites = emptyArray(),
                    adress = ""
                )
            )

            coreClient.sendPostRequest(
                EventUserData(
                    exchange = "new_user",
                    message = Message(
                    idUsuario = newUser.id.toString(),
                    nombreUsuario = "${newUser.name} ${newUser.lastName}",
                    correoElectronico = newUser.email,
                    numeroDeTelefono = newUser.phone,
                    direccionDefecto = newUser.adress,
                 )
              )
            )

            return RegisterResponse(
                code = "200",
                message = "Se registro correctamente",
                userCaseId = "usuario_guardado",
                sessionEnc = ""
            )
        } else {
            throw UserRegistedException("USER_WITH_EMAIL:${jwtRequestStep1.email} is already registed")
        }
    }
}
