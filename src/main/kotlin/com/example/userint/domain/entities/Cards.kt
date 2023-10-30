package com.example.userint.domain.entities

import com.vladmihalcea.hibernate.type.array.StringArrayType
import org.hibernate.annotations.Type
import org.hibernate.annotations.TypeDef
import java.time.Instant
import java.util.*
import javax.persistence.*

@Entity
@TypeDef(name = "array", typeClass = StringArrayType::class)
@Table(name = "cards")
open class Cards(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    open var id: Long = 0,

    @Column(name = "card_number", nullable = false)
    open var cardNumber: String,

    @Column(name = "card_operator", nullable = false)
    open var cardOperator: String,

    @Column(name = "card_expiration_date", nullable = false)
    open var cardExpirationDate: String,

    @Column(name = "card_cvv", nullable = false)
    open var cardCVV: String,

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "users_id")
    open var userId: Users,

    )
