package com.example.userint.domain.entities

import com.vladmihalcea.hibernate.type.array.StringArrayType
import org.hibernate.annotations.Type
import org.hibernate.annotations.TypeDef
import java.time.Instant
import java.util.*
import javax.persistence.*

@Entity
@TypeDef(name = "array", typeClass = StringArrayType::class)
@Table(name = "trips")
open class Trips(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    open var id: Long = 0,

    @Column(name = "since_trip", nullable = false)
    open var since: String,

    @Column(name = "from_trip")
    open var from: String? = null,

    @Column(name = "is_mobility_reduce")
    open var isMobilityReduce: Boolean = false,

    @Column(name = "payment_type", nullable = true)
    open var paymentType: String?,

    @Column(name = "code")
    open var code: UUID = UUID.randomUUID(),

    @Column(name = "distancia")
    open var distancia: Float? = null,

    @Column(name = "precio")
    open var precio: Float? = null,

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "users_id")
    open var userId: Users,

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "drivers_id", nullable = true)
    open var driverId: Drivers?,

    @Column(name = "is_cancel")
    open var is_cancel: Boolean = false,

    @Column(name = "status")
    open var status: String,

    @Column(name = "is_finished")
    open var is_finished: Boolean = false,

    @Column(name = "created_at", nullable = false)
    open var created_at: Instant = Instant.now(),

    @Column(name = "updated_at", nullable = false)
    open var updated_at: Instant = Instant.now(),
    )

