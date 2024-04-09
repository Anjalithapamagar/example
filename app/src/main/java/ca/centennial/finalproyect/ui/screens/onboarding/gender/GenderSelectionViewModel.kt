package ca.centennial.finalproyect.ui.screens.onboarding.gender

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.centennial.finalproyect.data.local.SharedPreferencesInterface
import ca.centennial.finalproyect.domain.Gender
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GenderSelectionViewModel @Inject constructor(private val defaultSharedPreferences: SharedPreferencesInterface) :
    ViewModel() {

    var selectedGender by mutableStateOf<Gender>(defaultSharedPreferences.getGender())
        private set

    private var _uiEvent = Channel<GenderSelectionEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: GenderSelectionUIEvent) {
        when (event) {
            is GenderSelectionUIEvent.OnGenderSelected -> {
                selectedGender = event.gender
                defaultSharedPreferences.saveGender(event.gender)
            }

            is GenderSelectionUIEvent.OnNextSelected -> {
                viewModelScope.launch {
                    _uiEvent.send(GenderSelectionEvent.NavigateUser)
                }
            }
        }
    }

    sealed class GenderSelectionUIEvent() {
        data class OnGenderSelected(val gender: Gender) : GenderSelectionUIEvent()
        object OnNextSelected : GenderSelectionUIEvent()
    }

    sealed class GenderSelectionEvent() {
        object NavigateUser : GenderSelectionEvent()
    }

}