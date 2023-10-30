package com.example.userint.domain.entities

import java.time.Instant
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "reviews")
open class Reviews(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    open var id: Long = 0,

    @Column(name = "user_code")
    open var userCode: UUID,

    @Column(name = "restaurant_code")
    open var restaurantCode: UUID,

    @Column(name = "rating")
    open var rating: Int,

    @Column(name = "comment")
    open var comment: String,

    @Column(name = "description")
    open var description: String?,

    @Column(name = "image")
    open var image: String?,

    @Column(name = "userName")
    open var userName: String?,

    @Column(name = "created_at", nullable = false)
    open var created_at: Instant = Instant.now(),
)
