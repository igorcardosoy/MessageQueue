import java.time.format.DateTimeFormatter

class Consumer(private val topic: String) {
    companion object {
         private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
    }

    fun consume(mq: MessageQueue) {
        val queue = mq.subscribe(topic)

        while (true) {
            val message = queue.take()
            println("${Colors.RED.getString()}${Thread.currentThread().threadId()} - Consumiu em $topic - ${message.content} ${message.time.format(formatter)} ${Colors.RESET.getString()}")
            Thread.sleep(2000L)
        }
    }
}