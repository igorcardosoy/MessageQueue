package model

import model.messageQueue.Message
import model.messageQueue.MessageQueue
import model.utils.Colors
import java.lang.Math.random
import java.net.HttpURLConnection
import java.net.URI
import java.time.ZoneId
import java.time.ZonedDateTime
import kotlin.random.Random

class Producer (private val local: List<String>) {

    fun produce(mq: MessageQueue) {
        while (true) {
            val local = local.random()
            val user = makeReq().random()+"@gmail.com"
            val text = makeReq()
            val message = Message(
                user = user,
                content = text,
                time = ZonedDateTime.now(ZoneId.of(local)),
                local = local
            )

            if (mq.publish(message)) {
                println("${Colors.GREEN.getString()}${Thread.currentThread().threadId()} - ${message.user} - ${message.local}\n ${message.time}  ${Colors.RESET.getString()}")
                Thread.sleep(1000L)
            } else {
                println("${Colors.ANSI_RED_BACKGROUND.getString()}${Thread.currentThread().threadId()} ${message.user} - FALHA NA PUBLICACAO - FILA CHEIA ${Colors.RESET.getString()}")
            }
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
    private fun makeChar(): Char {
        var d: Char = ' '
        while (d == ' '){
            d = Random.nextInt().toChar()
            if (d != ' '){
                return d;
            }
        }
        return 'T'
    }

}