package model.messageQueue

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.LinkedBlockingQueue

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

    fun subscribe(topic: String): LinkedBlockingQueue<Message> {
        synchronized(this){
            return queues.getOrPut(topic) {
                LinkedBlockingQueue(10)
            }
        }
    }
}