package ca.centennial.finalproyect.data.remote.mapper

import ca.centennial.finalproyect.data.remote.dto.Product
import ca.centennial.finalproyect.domain.TrackableFood
import kotlin.math.roundToInt

fun Product.toTrackableFood(): TrackableFood {
    val carbsPer100g = nutriments.carbohydrates100g.roundToInt()
    val proteinPer100g = nutriments.proteins100g.roundToInt()
    val fatPer100g = nutriments.proteins100g.roundToInt()
    val caloriesPer100g = nutriments.energyKcal100g.roundToInt()

    return TrackableFood(
        productName = productName,
        imageUrl = productImage,
        caloriesPer100g = caloriesPer100g,
        carbsPer100g = carbsPer100g,
        proteinPer100g = proteinPer100g,
        fatPer100g = fatPer100g
    )
}