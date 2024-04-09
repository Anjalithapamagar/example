package ca.centennial.finalproyect.ui.screens

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import ca.centennial.finalproyect.data.local.DefaultSharedPreferences
import dagger.hilt.android.lifecycle.HiltViewModel


import androidx.lifecycle.MutableLiveData

import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val sharedPreferences: DefaultSharedPreferences
) : ViewModel() {

    private val _isOnBoardingShown = MutableLiveData<Boolean>()
    val isOnBoardingShown: LiveData<Boolean> = _isOnBoardingShown

    init {
        // Inicializa el valor basado en las preferencias compartidas
        _isOnBoardingShown.value = sharedPreferences.getIsOnBoardingShown()
    }

    fun setOnBoardingShown() {
        sharedPreferences.setOnBoardingShown(true)
        _isOnBoardingShown.value = true
    }
}