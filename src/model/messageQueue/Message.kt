package model.messageQueue

import java.time.ZonedDateTime

data class Message(
    val user: String,
    val content: String,
    val time: ZonedDateTime,
    val local: String
)