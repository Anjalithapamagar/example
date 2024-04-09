package ca.centennial.finalproyect.domain

sealed class Gender(val value: String) {
    object Male : Gender("Male")
    object Female : Gender("Female")

    companion object {
        fun getGenderFromString(value: String): Gender {
            return when (value) {
                "Male" -> Male
                "Female" -> Female
                else -> Male
            }
        }
    }
}


