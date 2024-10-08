package model

import model.messageQueue.MessageQueue
import model.utils.Colors
import java.time.format.DateTimeFormatter

class Consumer(private val local: String) {
    companion object {
         private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
    }

    fun consume(mq: MessageQueue) {
        val queue = mq.subscribe(local)

        while (true) {
            val message = queue.take()
            println("${Colors.RED.getString()}${Thread.currentThread().threadId()} - ${message.user} - ${message.local} \n ${message.content} \n ${message.time.format(
                formatter
            )} ${Colors.RESET.getString()}")
            Thread.sleep(2000L)
        }
    }
}