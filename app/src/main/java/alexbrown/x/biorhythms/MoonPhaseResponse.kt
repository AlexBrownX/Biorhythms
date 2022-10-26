package alexbrown.x.biorhythms

data class MoonPhaseResponse(
    val phase: Map<String, MoonPhaseDetail>
)

data class MoonPhaseDetail(
    val phaseName: String,
    val lighting: Double,
    val isPhaseLimit: String,
    val svg: String
)