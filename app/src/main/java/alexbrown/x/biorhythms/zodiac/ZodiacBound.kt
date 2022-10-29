package alexbrown.x.biorhythms.zodiac

import java.util.GregorianCalendar

data class ZodiacBound(
    val startDate: GregorianCalendar,
    val endDate: GregorianCalendar,
    val zodiac: Zodiac
)
