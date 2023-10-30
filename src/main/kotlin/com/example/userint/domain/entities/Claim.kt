package com.example.userint.domain.entities

import com.vladmihalcea.hibernate.type.array.StringArrayType
import org.hibernate.annotations.Type
import org.hibernate.annotations.TypeDef
import java.time.Instant
import java.util.*
import javax.persistence.*

@Entity
@TypeDef(name = "array", typeClass = StringArrayType::class)
@Table(name = "claim")
open class Claim(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    open var id: Long = 0,

    @Column(name = "code")
    open var code: UUID = UUID.randomUUID(),

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "users_id")
    open var userId: Users,

    @Column(name = "driver_name")
    open var driverName: String?,

    @Column(name = "trip_code")
    open var tripCode: String,

    @Column(name = "status")
    open var status: String,

    @Column(name = "title")
    open var title: String,

    @Column(name = "description")
    open var description: String,

    @Column(name = "is_finished")
    open var is_finished: Boolean = false,

    @Column(name = "created_at", nullable = false)
    open var created_at: Instant = Instant.now(),

    @Column(name = "updated_at", nullable = false)
    open var updated_at: Instant = Instant.now(),
    )

