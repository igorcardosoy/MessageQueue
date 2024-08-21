import java.net.HttpURLConnection
import java.net.URI
import java.net.URL
import java.time.ZoneId
import java.time.ZonedDateTime

class Producer (private val topics: List<String>) {

    fun produce(mq: MessageQueue) {
        while (true) {
            val topic = topics.random()
            val text = makeReq()
            val message = Message(
                content = text,
                time = ZonedDateTime.now(ZoneId.of(topic)),
                topic = topic
            )

            if (mq.publish(message)) {
                println("${Colors.GREEN.getString()}${Thread.currentThread().threadId()} - Publicou em $topic - ${message.content} ${message.time} ${Colors.RESET.getString()}")
                Thread.sleep(1000L)
            } else {
                println("${Colors.ANSI_RED_BACKGROUND.getString()}Falha ao publicar no $topic: ${message.content} ${Colors.RESET.getString()}")
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
}