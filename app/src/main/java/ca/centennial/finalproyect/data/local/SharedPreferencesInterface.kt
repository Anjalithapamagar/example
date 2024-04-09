package ca.centennial.finalproyect.data.local

import ca.centennial.finalproyect.domain.Gender
import ca.centennial.finalproyect.domain.Goal

interface SharedPreferencesInterface {
    fun saveGender(gender: Gender)
    fun getGender(): Gender

    fun saveUserWeight(value: String)
    fun getUserWeight(): String

    fun saveUserHeight(value: String)
    fun getUserHeight(): String

    fun saveUserGoal(goal: Goal)
    fun getUserGoal(): Goal

    fun savePercentageCarbs(value: String)
    fun getPercentageCarbs(): String
    fun savePercentageProteins(value: String)
    fun getPercentageProteins(): String
    fun savePercentageFats(value: String)
    fun getPercentageFats(): String

    fun setOnBoardingShown(isOnboardingShown: Boolean)
    fun getIsOnBoardingShown(): Boolean
}