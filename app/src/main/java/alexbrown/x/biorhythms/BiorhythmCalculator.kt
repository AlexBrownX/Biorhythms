package alexbrown.x.biorhythms

import java.math.BigDecimal
import java.math.RoundingMode
import java.util.Date
import java.util.concurrent.TimeUnit
import kotlin.math.abs
import kotlin.math.sin

class BiorhythmCalculator(val dateTimeStorage: DateTimeStorage) {

    enum class Calculation(val numberOfDays: Int) {
        PHYSICAL(23),
        EMOTIONAL(28),
        INTELLECTUAL(33)
    }

    fun calculate(): CalculationResults {
        val daysAlive = getNumberOfDaysAlive(dateTimeStorage.savedDateTime)
        val physicalScore = calculateScore(Calculation.PHYSICAL, daysAlive)
        val emotionalScore = calculateScore(Calculation.EMOTIONAL, daysAlive)
        val intellectualScore = calculateScore(Calculation.INTELLECTUAL, daysAlive)

        return CalculationResults(
            BigDecimal(physicalScore).setScale(4, RoundingMode.HALF_EVEN),
            BigDecimal(emotionalScore).setScale(4, RoundingMode.HALF_EVEN),
            BigDecimal(intellectualScore).setScale(4, RoundingMode.HALF_EVEN)
        )
    }

    private fun getNumberOfDaysAlive(dateTimeOfBirth: Date): Long {
        val now = Date()
        val differentInMilliseconds = abs(now.time - dateTimeOfBirth.time)
        return TimeUnit.DAYS.convert(differentInMilliseconds, TimeUnit.MILLISECONDS);
    }

    // sin((2 * pi * daysAlive) / calculation.numberOfDays)
    private fun calculateScore(calculation: Calculation, daysAlive: Long): Double {
        return sin((2 * Math.PI * daysAlive) / calculation.numberOfDays)
    }
}