package ca.centennial.finalproyect.domain

data class TrackableFood(
    val productName: String,
    val imageUrl: String?,
    val caloriesPer100g: Int,
    val carbsPer100g: Int,
    val proteinPer100g: Int,
    val fatPer100g: Int
)
