package alexbrown.x.biorhythms

import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.Month
import kotlin.math.sin

class BiorhythmCalculator {

    enum class Calculation(val numberOfDays: Int) {
        PHYSICAL(23),
        EMOTIONAL(28),
        INTELLECTUAL(33)
    }

    init {
        val dateOfBirth = LocalDate.of(1984, Month.AUGUST, 21)
        val timeOfBirth = LocalTime.of(4, 0)
        val dateTimeOfBirth = LocalDateTime.of(dateOfBirth, timeOfBirth)

    }

    private fun calculate(dateTimeOfBirth: LocalDateTime): CalculationResults {
        val daysAlive = getNumberOfDaysAlive(dateTimeOfBirth)
        val physicalScore = calculateScore(Calculation.PHYSICAL, daysAlive)
        val emotionalScore = calculateScore(Calculation.EMOTIONAL, daysAlive)
        val intellectualScore = calculateScore(Calculation.INTELLECTUAL, daysAlive)

        return CalculationResults(physicalScore, emotionalScore, intellectualScore)
    }

    private fun getNumberOfDaysAlive(dateTimeOfBirth: LocalDateTime): Long {
        val now = LocalDateTime.now()
        return Duration.between(dateTimeOfBirth, now).toDays()
    }

    // sin((2 * pi * daysAlive) / calculation.numberOfDays)
    private fun calculateScore(calculation: Calculation, daysAlive: Long): Double {
        return sin((2 * Math.PI * daysAlive) / calculation.numberOfDays)
    }
}