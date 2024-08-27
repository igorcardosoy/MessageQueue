package model

import model.messageQueue.Message
import model.messageQueue.MessageQueue
import model.utils.Colors
import java.net.HttpURLConnection
import java.net.URI
import java.time.ZoneId
import java.time.ZonedDateTime

class Producer (private val local: List<String>) {

    fun produce(mq: MessageQueue) {
        while (true) {
            val local = local.random()
            val text = makeReq()
            val user = text.replace(" ", "").random()+"@gmail.com"
            val message = Message(
                user = user,
                content = text,
                time = ZonedDateTime.now(ZoneId.of(local)),
                local = local
            )

            while (!mq.publish(message)) {
                println("${Colors.ANSI_RED_BACKGROUND.getString()}${Thread.currentThread().threadId()} ${message.user} - FALHA NA PUBLICACAO NO ${message.time} - FILA CHEIA ${Colors.RESET.getString()}")

                Thread.sleep(500L)
            }

            println("${Colors.GREEN.getString()}${Thread.currentThread().threadId()} - ${message.user} - ${message.local}\n ${message.time}  ${Colors.RESET.getString()}")

            //Sleep para simular o tempo de produção
            Thread.sleep(1000L)
        }
    }

    private fun makeReq(): String {
        val url = URI("https://api.chucknorris.io/jokes/random").toURL()
        val connection = url.openConnection() as HttpURLConnection

        try {
            connection.requestMethod = "GET"

            val responseCode = connection.responseCode

            if (responseCode == HttpURLConnection.HTTP_OK) {
                val inputStream = connection.inputStream
                val response = inputStream.bufferedReader().use { it.readText() }
                return response.split("\"value\":\"")[1].split("\"")[0]
            } else {
                println("Erro na requisição: $responseCode")
            }
        } finally {
            connection.disconnect()
        }

        return "Erro na requisição"
    }
}