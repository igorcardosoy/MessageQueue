import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.locks.ReentrantLock

class MessageQueue {
    private val queues = ConcurrentHashMap<String, LinkedBlockingQueue<Message>>()
    private val lock = ReentrantLock()

    // Publish adiciona uma mensagem à fila do tópico especificado. Se o tópico não existir, cria uma nova fila.
    fun publish(message: Message) {
        lock.lock()
        try {
            val queue = queues.getOrPut(message.topic) {
                LinkedBlockingQueue(10) // Default value para caso o tópico não exista.
            }

            queue.put(message) // Adiciona a mensagem à fila do tópico.
        } finally {
            lock.unlock()
        }
    }

    // Subscribe retorna a fila de mensagens do tópico especificado. Cria uma nova fila se o tópico não existir.
    fun subscribe(topic: String): LinkedBlockingQueue<Message> {
        lock.lock()
        try {
            return queues.getOrPut(topic) {
                LinkedBlockingQueue(10) // Default value para o retorno do map, se o tópico não existir.
            }
        } finally {
            lock.unlock()
        }
    }
}