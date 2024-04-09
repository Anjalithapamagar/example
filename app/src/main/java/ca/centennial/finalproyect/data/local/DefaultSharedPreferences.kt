package ca.centennial.finalproyect.data.local

import android.content.SharedPreferences
import androidx.core.content.edit
import  ca.centennial.finalproyect.domain.Gender
import ca.centennial.finalproyect.domain.Goal
import javax.inject.Inject

class DefaultSharedPreferences @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : SharedPreferencesInterface {

    override fun saveGender(gender: Gender) {
        sharedPreferences.edit {
            putString(PREF_GENDER, gender.value)
            commit()
        }
    }

    override fun getGender(): Gender {
        val gender = sharedPreferences.getString(PREF_GENDER, Gender.Male.value)
        return gender?.let { Gender.getGenderFromString(it) } ?: Gender.Male
    }

    override fun saveUserWeight(value: String) {
        sharedPreferences.edit {
            putString(PREF_USER_WEIGHT, value)
            commit()
        }
    }

    override fun getUserWeight(): String {
        return sharedPreferences.getString(PREF_USER_WEIGHT, "80")!!
    }

    override fun saveUserHeight(value: String) {
        sharedPreferences.edit {
            putString(PREF_USER_HEIGHT, value)
            commit()
        }
    }

    override fun getUserHeight(): String {
        return sharedPreferences.getString(PREF_USER_HEIGHT, "180")!!
    }

    override fun saveUserGoal(goal: Goal) {
        sharedPreferences.edit {
            putString(PREF_USER_GOAL, goal.value)
            commit()
        }
    }

    override fun getUserGoal(): Goal {
        val userGoal = sharedPreferences.getString(PREF_USER_GOAL, Goal.Loose.value)
        return userGoal?.let { Goal.fromString(it) } ?: Goal.Loose
    }

    override fun savePercentageCarbs(value: String) {
        sharedPreferences.edit {
            putString(PREF_USER_CARBS_PERCENTAGE, value)
            commit()
        }
    }

    override fun getPercentageCarbs(): String {
        return sharedPreferences.getString(PREF_USER_CARBS_PERCENTAGE, "40")!!
    }

    override fun savePercentageProteins(value: String) {
        sharedPreferences.edit {
            putString(PREF_USER_CARBS_PROTEIN, value)
            commit()
        }
    }

    override fun getPercentageProteins(): String {
        return sharedPreferences.getString(PREF_USER_CARBS_PROTEIN, "30")!!
    }

    override fun savePercentageFats(value: String) {
        sharedPreferences.edit {
            putString(PREF_USER_CARBS_FATS, value)
            commit()
        }
    }

    override fun getPercentageFats(): String {
        return sharedPreferences.getString(PREF_USER_CARBS_FATS, "30")!!
    }

    override fun setOnBoardingShown(isOnboardingShown: Boolean) {
        sharedPreferences.edit {
            putBoolean(PREF_IS_ONBOARDING_SHOWN, isOnboardingShown)
            commit()
        }
    }

    override fun getIsOnBoardingShown(): Boolean {
        return sharedPreferences.getBoolean(PREF_IS_ONBOARDING_SHOWN, false)
    }

    companion object {
        const val PREF_GENDER = "prefGender"
        const val PREF_USER_WEIGHT = "prefWeight"
        const val PREF_USER_HEIGHT = "prefHeight"
        const val PREF_USER_GOAL = "prefGoal"

        const val PREF_USER_CARBS_PERCENTAGE = "percentageCarbs"
        const val PREF_USER_CARBS_PROTEIN = "percentageProtein"
        const val PREF_USER_CARBS_FATS = "percentageFats"

        const val PREF_IS_ONBOARDING_SHOWN = "prefIsOnboardingShown"

    }
}