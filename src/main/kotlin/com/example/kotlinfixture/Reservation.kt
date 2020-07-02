package com.example.kotlinfixture

import javax.persistence.*

@Entity
data class Reservation(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L,

    val userName: String,

    @OneToOne(mappedBy = "reservation", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    val reservationPackage: ReservationPackage
)