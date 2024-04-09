package ca.centennial.finalproyect.domain

sealed class Goal(val value: String) {
    object Loose : Goal("Loose")
    object Keep : Goal("Keep")
    object Gain : Goal("Gain")

    companion object {
        fun fromString(value: String): Goal {
            return when (value) {
                "Loose" -> Loose
                "Keep" -> Keep
                "Gain" -> Gain
                else -> Loose
            }
        }
    }
}
