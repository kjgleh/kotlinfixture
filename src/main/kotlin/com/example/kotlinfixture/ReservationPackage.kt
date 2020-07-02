package com.example.kotlinfixture

import java.util.*
import javax.annotation.Generated
import javax.persistence.*

@Entity
data class ReservationPackage(
    @Id
    var id: Long = 0L,
    val title: String
) {
    @OneToOne
    @MapsId
    lateinit var reservation: Reservation
}
