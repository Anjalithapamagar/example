package ca.centennial.finalproyect.ui.navigation

sealed class Routes(val route: String) {
    object Login : Routes("Login Screen")
    object Home : Routes("Home Screen")
    object SignUp : Routes("SignUp Screen")
    object ForgotPassword : Routes("ForgotPassword Screen")
    object ProfileScreen : Routes("Profile Screen")
}