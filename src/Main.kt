import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import kotlin.concurrent.thread
import kotlin.system.exitProcess

val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
val RESET = "\u001B[0m"
val RED = "\u001B[31m"
val GREEN = "\u001B[32m"


fun main() {
    val mq = MessageQueue()
    val topics = listOf("GMT-3", "GMT-2", "GMT-1", "GMT", "GMT+1", "GMT+2", "GMT+3", "GMT+4")

    val producerCount = 4
    val consumerCount = 8
    repeat(producerCount) {
        thread { Producer(topics).produce(mq) }
    }

    repeat(consumerCount) {
        thread { Consumer(topics.random()).consume(mq) }
    }

    Thread.sleep(15000L)

    // Finaliza o sistema
    println("${RED}---${GREEN}FINALIZANDO${RED}---${RESET}")
    exitProcess(0)
}