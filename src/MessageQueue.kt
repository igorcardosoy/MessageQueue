import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.locks.ReentrantLock

class MessageQueue {
    private val queues = ConcurrentHashMap<String, LinkedBlockingQueue<Message>>()

    fun publish(message: Message): Boolean {
        synchronized(this){
            val queue = queues.getOrPut(message.topic) {
               LinkedBlockingQueue(10)
           }
            try {
                return queue.add(message)
            } catch (e: IllegalStateException) {
                return false
            }
        }
    }

    // Subscribe retorna a fila de mensagens do tópico especificado. Cria uma nova fila se o tópico não existir.
    fun subscribe(topic: String): LinkedBlockingQueue<Message> {
        synchronized(this){
            return queues.getOrPut(topic) {
                LinkedBlockingQueue(10) // Default value para o retorno do map, se o tópico não existir.
            }
        }
    }
}