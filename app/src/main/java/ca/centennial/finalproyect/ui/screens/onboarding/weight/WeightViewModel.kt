package ca.centennial.finalproyect.ui.screens.onboarding.weight

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.centennial.finalproyect.data.local.DefaultSharedPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeightViewModel @Inject constructor(private val sharedPreferences: DefaultSharedPreferences) :
    ViewModel() {

    private var _uiState = Channel<Event>()
    val uiState = _uiState.receiveAsFlow()

    var userWeight by mutableStateOf(sharedPreferences.getUserWeight())
        private set

    fun onEvent(event: UIEvent) {
        when (event) {
            UIEvent.OnNextClick -> {
                viewModelScope.launch {
                    _uiState.send(Event.NavigateUser)
                }
            }

            is UIEvent.UpdateUserWeight -> {
                viewModelScope.launch {
                    if (event.value.length <= 5) {
                        userWeight = event.value
                        sharedPreferences.saveUserWeight(userWeight)
                    }
                }
            }
        }
    }

    sealed class UIEvent() {
        data class UpdateUserWeight(val value: String) : UIEvent()
        object OnNextClick : UIEvent()
    }

    sealed class Event {
        object NavigateUser : Event()
    }
}