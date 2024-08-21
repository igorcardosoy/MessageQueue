import java.time.ZonedDateTime

data class Message(
    val content: String,
    val time: ZonedDateTime,
    val topic: String
)