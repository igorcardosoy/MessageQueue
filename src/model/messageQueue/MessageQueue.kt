package model.messageQueue

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.LinkedBlockingQueue

class MessageQueue {
    private val queues = ConcurrentHashMap<String, LinkedBlockingQueue<Message>>()

    fun publish(message: Message): Boolean {
        synchronized(this){
            val queue = queues.getOrPut(message.local) {
               LinkedBlockingQueue(10)
           }

            return queue.offer(message)
        }
    }

    fun subscribe(local: String): LinkedBlockingQueue<Message> {
        synchronized(this){
            return queues.getOrPut(local) {
                LinkedBlockingQueue(10)
            }
        }
    }
}