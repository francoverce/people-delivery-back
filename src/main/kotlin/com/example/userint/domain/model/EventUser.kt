package com.example.userint.domain.model

data class EventUserData (
    val exchange:String,
    val message:Message
)

data class Message (
    val idUsuario: String,
    val nombreUsuario: String?,
    val correoElectronico: String?,
    val numeroDeTelefono: String?,
    val direccionDefecto: String?,
    val operadoraTarjeta: String? = null,
    val nroTarjeta: String? = null,
    val vencimientoTarjeta: String? = null,
    val codigoSeguridadTarjeta: String? = null,
)