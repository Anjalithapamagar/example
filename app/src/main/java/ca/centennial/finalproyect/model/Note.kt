package ca.centennial.finalproyect.model

data class Note(
    var id: String? = null,
    var userId: String = "",
    val title: String = "",
    val content: String = "",
    val height: String = "",
    val weight: String = "",
    val bmiResult: String = "",
    val bmiCategory: String = ""

)