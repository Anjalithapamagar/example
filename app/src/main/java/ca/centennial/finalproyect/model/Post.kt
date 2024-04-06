package ca.centennial.finalproyect.model

import java.util.Date

data class Post(
    val author: String = "",
    val content: String = "",
    val timeStamp: Date,
    var likes: Int = 0,
//    val comments: List<Comment> = emptyList()
)
