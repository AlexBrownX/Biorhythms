package alexbrown.x.biorhythms.biorhythm

import java.math.BigDecimal
import java.util.Date

data class CalculationResults(
    val startDate: Date,
    val endDate: Date,
    val physicalScore: BigDecimal,
    val emotionalScore: BigDecimal,
    val intellectualScore: BigDecimal
)
