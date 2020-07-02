package com.example.kotlinfixture

import org.springframework.data.jpa.repository.JpaRepository

interface ReservationRepository: JpaRepository<Reservation, Long>