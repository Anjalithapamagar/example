package ca.centennial.finalproyect.ui.screens.onboarding.height

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
import javax.inject.Inject

@HiltViewModel
class HeightViewModel @Inject constructor(private val sharedPreferences: DefaultSharedPreferences) :
    ViewModel() {

    private var _uiState = Channel<UiEvent>()
    val uiState = _uiState.receiveAsFlow()

    var userHeight by mutableStateOf(sharedPreferences.getUserHeight())
        private set

    fun onEvent(event: HeightStateEvent) {
        when (event) {
            HeightStateEvent.OnNextClick -> {
                viewModelScope.launch {
                    _uiState.send(UiEvent.NavigateUser)
                }
            }

            is HeightStateEvent.UpdateUserHeight -> {
                viewModelScope.launch {
                    if (event.value.length <= 5) {
                        userHeight = event.value
                        sharedPreferences.saveUserHeight(userHeight)
                    }
                }
            }
        }
    }

    sealed class HeightStateEvent() {
        data class UpdateUserHeight(val value: String) : HeightStateEvent()
        object OnNextClick : HeightStateEvent()
    }

}