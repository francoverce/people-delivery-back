package com.example.userint.domain.entities

import com.vladmihalcea.hibernate.type.array.StringArrayType
import org.hibernate.annotations.Type
import org.hibernate.annotations.TypeDef
import java.time.Instant
import java.util.*
import javax.persistence.*

@Entity
@TypeDef(name = "array", typeClass = StringArrayType::class)
@Table(name = "users")
open class Users(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    open var id: Long = 0,

    @Column(name = "email", nullable = false)
    open var email: String,

    @Column(name = "user_name")
    open var userName: String? = null,

    @Column(name = "phone")
    open var phone: String? = null,

    @Column(name = "name")
    open var name: String? = null,

    @Column(name = "last_name")
    open var lastName: String? = null,

    @Column(name = "password", nullable = false)
    open var password: String,

    @Column(name = "code")
    open var code: UUID = UUID.randomUUID(),

    @Column(name = "rol", nullable = false)
    open var rol: String,

    @Column(name = "icon")
    open var icon: String?,

    @Column(name = "created_at", nullable = false)
    open var created_at: Instant = Instant.now(),

    @Column(name = "updated_at", nullable = false)
    open var updated_at: Instant = Instant.now(),

    @Column(name = "favorites")
    @Type(type = "array")
    open var favorites: Array<String>?,

    @Column(name = "otpCode", nullable = true)
    open var otpCode: String? = null,

    @Column(name = "dateValidationOTP", nullable = true)
    open var dateValidationOTP: Instant? = null,

    @Column(name = "adress")
    open var adress: String?,
)
