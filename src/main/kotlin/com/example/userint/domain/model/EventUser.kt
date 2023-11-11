package com.example.userint.domain.model

data class EventNewUserData (
    val exchange:String,
    val message:MessageNewUser
)

data class MessageNewUser (
    val id: Long,
    val telefono: String?,
    val usuario: String?,
    val nombre: String?,
    val apellido: String,
    val email: String,
    val direccion: String,
)



data class EventUpdateUserData (
    val exchange:String,
    val message:MessageUpdateUser
)
data class MessageUpdateUser (
    val id: Long,
    val telefono: String?,
    val usuario: String?,
    val nombre: String?,
    val apellido: String,
    val email: String,
    val direccion: String,
)



data class EventUpdateUserDataWithCard (
    val exchange:String,
    val message:MessageUpdateUserWithCard
)
data class MessageUpdateUserWithCard (
    val id: Long,
    val telefono: String?,
    val usuario: String?,
    val nombre: String?,
    val apellido: String,
    val email: String,
    val direccion: String,
    val operadoraTarjeta : String?,
    val nroTarjeta :String?,
    val vencimientoTarjeta :String?,
    val codigoSeguridadTarjeta :String?
)