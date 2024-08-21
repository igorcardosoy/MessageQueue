import java.time.ZoneId
import java.time.ZonedDateTime

class Producer (private val topics: List<String>) {

    fun produce(mq: MessageQueue) {
        while (true) {
            val topic = topics.random()
            val message = Message(
                content = ZonedDateTime.now(ZoneId.of(topic)),
                topic = topic
            )

            if (mq.publish(message)) {
                println("Produtor publicou no $topic: ${message.content}")
                Thread.sleep(1000L)
            } else {
                println("Falha ao publicar no $topic: ${message.content}")
            }
        }
    }
}