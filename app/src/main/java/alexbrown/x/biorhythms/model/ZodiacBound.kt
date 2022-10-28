package alexbrown.x.biorhythms.model

import java.util.GregorianCalendar

data class ZodiacBound(
    val startDate: GregorianCalendar,
    val endDate: GregorianCalendar,
    val zodiac: Zodiac
)
