package ca.centennial.finalproyect.data.local.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import java.util.Date

@Dao
interface FoodDao {

    @Query("SELECT * from FoodEntity")
    fun getFoodFromDb(): List<FoodEntity>

    @Upsert
    fun insertFood(foodEntity: FoodEntity)

    @Query("SELECT * from FoodEntity WHERE mealDate =:date AND mealId =:mealType")
    fun getFoodPerDay(date: Date, mealType: Int): List<FoodEntity>

    @Query("SELECT * from FoodEntity WHERE mealId =:mealType")
    fun getFoodPerMeal(mealType: Int): List<FoodEntity>

}