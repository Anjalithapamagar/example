package ca.centennial.finalproyect.model

data class Contact(
    val key: String? = null,
    val name: String = "",
    val email: String = "",
    val phoneNumber: String = "",
    val uid: String = "",
    val city: String =  "",
    val country: String = ""
)