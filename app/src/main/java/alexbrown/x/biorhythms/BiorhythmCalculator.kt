package alexbrown.x.biorhythms

import java.math.BigDecimal
import java.math.RoundingMode
import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar
import java.util.concurrent.TimeUnit
import kotlin.math.abs
import kotlin.math.sin

class BiorhythmCalculator {

    enum class Calculation(val numberOfDays: Int) {
        PHYSICAL(23),
        EMOTIONAL(28),
        INTELLECTUAL(33)
    }

    fun calculate(startDate: Date, endDate: Date, daysOffset: Int): Collection<CalculationResults> {
        val results = mutableListOf<CalculationResults>()
        val dates = getDateRange(endDate, daysOffset)

        dates.forEach { date ->  results.add(calculate(startDate, date)) }

        return results
    }

    fun calculate(startDate: Date, endDate: Date): CalculationResults {
        val daysAlive = getNumberOfDaysAlive(startDate, endDate)
        val physicalScore = calculateScore(Calculation.PHYSICAL, daysAlive)
        val emotionalScore = calculateScore(Calculation.EMOTIONAL, daysAlive)
        val intellectualScore = calculateScore(Calculation.INTELLECTUAL, daysAlive)

        return CalculationResults(
            startDate,
            endDate,
            BigDecimal(physicalScore).setScale(4, RoundingMode.HALF_EVEN),
            BigDecimal(emotionalScore).setScale(4, RoundingMode.HALF_EVEN),
            BigDecimal(intellectualScore).setScale(4, RoundingMode.HALF_EVEN)
        )
    }

    private fun getNumberOfDaysAlive(startDate: Date, endDate: Date): Long {
        val differentInMilliseconds = abs(endDate.time - startDate.time)
        return TimeUnit.DAYS.convert(differentInMilliseconds, TimeUnit.MILLISECONDS);
    }

    private fun getDateRange(endDate: Date, daysOffset: Int): Collection<Date> {
        val dates = mutableListOf<Date>()

        for  (offset in daysOffset downTo 1) {
            val calendar = GregorianCalendar()
            calendar.setTime(endDate)
            calendar.add(Calendar.DATE, -offset)
            dates.add(calendar.time)
        }

        dates.add(endDate)

        for  (offset in 1 .. daysOffset) {
            val calendar = GregorianCalendar()
            calendar.setTime(endDate)
            calendar.add(Calendar.DATE, offset)
            dates.add(calendar.time)
        }

        return dates
    }

    // sin((2 * pi * daysAlive) / calculation.numberOfDays)
    private fun calculateScore(calculation: Calculation, daysAlive: Long): Double {
        return sin((2 * Math.PI * daysAlive) / calculation.numberOfDays)
    }
}