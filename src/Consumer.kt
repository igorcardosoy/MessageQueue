import java.time.format.DateTimeFormatter

class Consumer(private val topic: String) {
    private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")

    fun consume(mq: MessageQueue) {
        val queue = mq.subscribe(topic)

        while (true) {
            val message = queue.take()
            println("Consumidor no $topic consumiu: ${message.content.format(formatter)}")

            Thread.sleep(2000L)
        }
    }
}