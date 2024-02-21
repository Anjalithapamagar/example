package ca.centennial.finalproyect.model

data class Contact(
    val key: String? = null,
    var name: String = "",
    val email: String = "",
    var phoneNumber: String = "",
    var uid: String = "",
    var city: String =  "",
    var country: String = "",
    var lastName: String = "",
    var peso: Double = 0.0,
    var estatura: Double = 0.0
)