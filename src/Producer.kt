import java.net.HttpURLConnection
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
                println("${GREEN}${Thread.currentThread().threadId()} - Publicou em ${topic} - ${message.content} ${message.time} ${RESET}")
                Thread.sleep(1000L)
            } else {
                println("Falha ao publicar no $topic: ${message.content}")
            }
        }
    }

    private fun makeReq(): String {
        val url = URL("https://api.chucknorris.io/jokes/random")
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