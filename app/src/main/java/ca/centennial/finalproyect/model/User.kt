package ca.centennial.finalproyect.model

data class User(
    val uid: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val email: String ="",
    val dateOfBirth: String = "",
    val gender: String = "",
    val height: Double = 0.0,
    val weight: Double = 0.0,
    val initialBMI: Double = 0.0,
    val currentBMI: Double = 0.0,
    val bmiCategory: String = ""
)
