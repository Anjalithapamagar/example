package ca.centennial.finalproyect.utils

sealed class MealType(val mealType: String, val type: Int) {
    object BREAKFAST : MealType("Breakfast", 1)
    object LUNCH : MealType("Lunch", 2)
    object DINNER : MealType("Dinner", 3)
    object SNACK : MealType("Snack", 4)

    fun toMeal(): String {
        return when (this) {
            BREAKFAST -> BREAKFAST.mealType
            LUNCH -> LUNCH.mealType
            DINNER -> DINNER.mealType
            SNACK -> SNACK.mealType
        }
    }

    companion object {
        fun fromType(type: Int): String {
            return when (type) {
                BREAKFAST.type -> BREAKFAST.mealType
                LUNCH.type -> LUNCH.mealType
                DINNER.type -> DINNER.mealType
                SNACK.type -> SNACK.mealType
                else -> BREAKFAST.mealType
            }
        }


    }


}


