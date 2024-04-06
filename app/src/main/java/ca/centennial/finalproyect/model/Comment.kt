package ca.centennial.finalproyect.model

import java.util.Date

data class Comment(
    val id: String = "",
    val author: String = "",
    val content: String = "",
    val timeStamp: Date
)
