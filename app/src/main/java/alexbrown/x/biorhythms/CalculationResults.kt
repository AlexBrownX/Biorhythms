package alexbrown.x.biorhythms

import java.math.BigDecimal

data class CalculationResults(
    val physicalScore: BigDecimal,
    val emotionalScore: BigDecimal,
    val intellectualScore: BigDecimal
)
