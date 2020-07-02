package com.example.kotlinfixture

import com.appmattus.kotlinfixture.decorator.recursion.NullRecursionStrategy
import com.appmattus.kotlinfixture.decorator.recursion.recursionStrategy
import com.appmattus.kotlinfixture.kotlinFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import java.util.*

@DataJpaTest
internal class ReservationRepositoryTest {

    @Autowired
    private lateinit var reservationRepository: ReservationRepository

    private val fixture = kotlinFixture()

    @Test
    fun save_and_get_correctly() {
        // Arrange
        val reservationPackage = ReservationPackage(
            title = UUID.randomUUID().toString()
        )

        val reservation = Reservation(
            userName = UUID.randomUUID().toString(),
            reservationPackage = reservationPackage
        )

        reservationPackage.apply {
            this.reservation = reservation
        }

        // Act
        reservationRepository.save(reservation)

        // Assert
        val actual = reservationRepository.findById(reservation.id)
        assertThat(actual.get()).isEqualTo(reservation)
    }

    @Test
    fun save_and_get_with_kotlinfixture_correctly() {
        // Arrange
        val reservation = fixture<Reservation> {
            recursionStrategy(NullRecursionStrategy)
        }.apply {
            this.reservationPackage.id = this.id
            this.reservationPackage.reservation = this
        }

        // Act
        val actual = reservationRepository.save(reservation)

        // Assert
        assertThat(actual).isEqualToIgnoringGivenFields(reservation, "id", "reservationPackage")
        assertThat(actual.reservationPackage).isEqualToIgnoringGivenFields(reservation.reservationPackage, "id", "reservation")
    }
}