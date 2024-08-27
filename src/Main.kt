import model.*
import model.messageQueue.MessageQueue
import model.utils.Colors
import model.utils.GMT
import java.time.format.DateTimeFormatter
import kotlin.concurrent.thread
import kotlin.system.exitProcess

val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")


fun main() {
    val mq = MessageQueue()
    val local = GMT.entries.map { it.getString() }

    println("${Colors.ANSI_PURPLE.getString()}Digite a quantidade de produtores:${Colors.RESET.getString()}")
    val producerCount = readln().toInt()

    println("${Colors.ANSI_PURPLE.getString()}Digite a quantidade de consumidores:${Colors.RESET.getString()}")
    val consumerCount = readln().toInt()


    repeat(consumerCount) { i ->
        thread { Consumer(local[i % local.size]).consume(mq) }
    }

    repeat(producerCount) {
        thread { Producer(local).produce(mq) }
    }


    Thread.sleep(15000L)

    // Finaliza o sistema
    println("${Colors.RED.getString()}---${Colors.GREEN.getString()}FINALIZANDO${Colors.RED.getString()}---${Colors.RESET.getString()}")
    exitProcess(0)
}