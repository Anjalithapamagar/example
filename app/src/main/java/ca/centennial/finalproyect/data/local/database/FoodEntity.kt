package ca.centennial.finalproyect.data.local.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class FoodEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val mealId: Int,
    val calories: Int,
    val carbs: Int,
    val protein: Int,
    val fat: Int,
    val productName: String,
    val productImage: String?,
    val mealDate: Date,
    val quantity: Int
)