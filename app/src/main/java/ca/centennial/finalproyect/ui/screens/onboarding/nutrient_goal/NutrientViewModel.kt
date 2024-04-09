package ca.centennial.finalproyect.ui.screens.onboarding.nutrient_goal

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.centennial.finalproyect.data.local.DefaultSharedPreferences
import ca.centennial.finalproyect.utils.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class NutrientViewModel @Inject constructor(private val sharedPreferences: DefaultSharedPreferences) :
    ViewModel() {

    var carbsPercentage by mutableStateOf<String>(sharedPreferences.getPercentageCarbs())
        private set

    var proteinsPercentage by mutableStateOf<String>(sharedPreferences.getPercentageProteins())
        private set

    var fatsPercentage by mutableStateOf<String>(sharedPreferences.getPercentageFats())
        private set

    private var _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun updateCarbsPercentage(value: String) {
        if (value.length <= 2) {
            carbsPercentage = value
            sharedPreferences.savePercentageCarbs(value)
        }
    }

    fun updateProteinPercentage(value: String) {
        if (value.length <= 2) {
            proteinsPercentage = value
            sharedPreferences.savePercentageProteins(value)
        }
    }

    fun updateFatsPercentage(value: String) {
        if (value.length <= 2) {
            fatsPercentage = value
            sharedPreferences.savePercentageFats(value)
        }
    }

    fun navigate() {
        if (validatePercentage()) {
            viewModelScope.launch {
                _uiEvent.send(UiEvent.NavigateUser)
            }
        } else {
            Timber.i("Error")
            viewModelScope.launch {
                _uiEvent.send(UiEvent.ShowSnackbar(0))
            }
        }
    }

    private fun validatePercentage(): Boolean {
        val carbs = carbsPercentage.toIntOrNull() ?: 0
        val proteins = proteinsPercentage.toIntOrNull() ?: 0
        val fats = fatsPercentage.toIntOrNull() ?: 0
        return carbs + proteins + fats == 100
    }
}