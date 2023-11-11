package com.example.userint.domain.entities

import com.vladmihalcea.hibernate.type.array.StringArrayType
import org.hibernate.annotations.TypeDef
import java.time.Instant
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@TypeDef(name = "array", typeClass = StringArrayType::class)
@Table(name = "drivers")
open class Drivers(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    open var id: Long = 0,

    @Column(name = "id_chofer", nullable = false)
    open var idChofer: Long,

    @Column(name = "full_name")
    open var fullName: String? = null,

    @Column(name = "car", nullable = false)
    open var car: String,

    @Column(name = "patent", nullable = false)
    open var patent: String,

    @Column(name = "date_come", nullable = false)
    open var dateCome: LocalDateTime,

    @Column(name = "icon")
    open var icon: String?,

    @Column(name = "created_at", nullable = false)
    open var created_at: Instant = Instant.now(),

    @Column(name = "updated_at", nullable = false)
    open var updated_at: Instant = Instant.now(),

    )
