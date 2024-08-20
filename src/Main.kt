import kotlin.concurrent.thread
import kotlin.system.exitProcess


// Producer simula um produtor de mensagens que publica mensagens em tópicos aleatórios.
fun producer(mq: MessageQueue, topics: List<String>) {
    while (true) {
        val topic = topics.random() // Seleciona um tópico aleatório da lista
        val message = Message(
            content = "Mensagem no $topic às ${System.currentTimeMillis()}",
            topic = topic
        )

        mq.publish(message) // Publica a mensagem na fila
        println("Publicado: ${message.content}")

        Thread.sleep(1000L) // Simula algum trabalho com um sleep
    }
}

// Consumer simula um consumidor de mensagens que processa mensagens de um tópico específico.
fun consumer(mq: MessageQueue, topic: String) {
    val queue = mq.subscribe(topic) // Inscreve-se na fila de mensagens do tópico

    while (true) {
        val message = queue.take() // Remove e retorna a cabeça da fila, bloqueando se necessário
        println("Consumidor no $topic consumiu: ${message.content}")

        Thread.sleep(2000L) // Simula algum trabalho com um sleep
    }
}

fun main() {
    val mq = MessageQueue() // Inicializa a fila de mensagens
    val topics = listOf("topic1", "topic2", "topic3", "topic4", "topic5", "topic6", "topic7", "topic8")

    val producerCount = 4
    val consumerCount = 8

    // Inicia múltiplos produtores
    repeat(producerCount) {
        thread { producer(mq, topics) }
    }

    // Inicia múltiplos consumidores, alternando entre os tópicos
    repeat(consumerCount) { i ->
        thread { consumer(mq, topics[i % topics.size]) }
    }

    // Simula o tempo de execução do sistema
    Thread.sleep(15000L) // Espera por 15 segundos para simular a execução

    // Finaliza o sistema
    println("Finalizando...")
    exitProcess(0) // Termina o programa
}