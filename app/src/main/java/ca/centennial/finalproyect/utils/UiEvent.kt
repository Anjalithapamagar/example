package ca.centennial.finalproyect.utils

sealed class UiEvent {
    object NavigateUser : UiEvent()
    data class ShowSnackbar(val messageId: Int) : UiEvent()
}
