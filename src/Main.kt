import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import kotlin.concurrent.thread
import kotlin.system.exitProcess

val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
val RESET = "\u001B[0m"
val RED = "\u001B[31m"
val GREEN = "\u001B[32m"

fun producer(mq: MessageQueue, topics: List<String>) {
    while (true) {
        val topic = topics.random()
        val message = Message(
            content = ZonedDateTime.now(ZoneId.of(topic)),
            topic = topic
        )

        if (mq.publish(message)) {
//            println(ANSI_RED+"Produtor publicou no $topic: ${message.content}")
            println("${GREEN}${Thread.currentThread().threadId()} - Publicou em ${topic}: ${message.content}${RESET}")
            Thread.sleep(1000L) // Simula algum trabalho com um sleep
        } else {
            println("Falha ao publicar no $topic: ${message.content}")
        }
    }
}

fun consumer(mq: MessageQueue, topic: String) {
    val queue = mq.subscribe(topic)

    while (true) {
        val message = queue.take()
//        println("Consumidor no $topic consumiu: ${message.content.format(formatter)}"
        println("${RED}${Thread.currentThread().threadId()} - Consumiu em ${topic}: ${message.content.format(formatter)} ${RESET}")

        Thread.sleep(2000L)
    }
}

fun main() {
    val mq = MessageQueue()
    val topics = listOf("GMT-3", "GMT-2", "GMT-1", "GMT", "GMT+1", "GMT+2", "GMT+3", "GMT+4")

    val producerCount = 4
    val consumerCount = 8
    repeat(producerCount) {
        thread { producer(mq, topics) }
    }

    repeat(consumerCount) { i ->
        thread { consumer(mq, topics[i % topics.size]) }
    }

    Thread.sleep(15000L)

    // Finaliza o sistema
    println("Finalizando...")
    exitProcess(0)
}