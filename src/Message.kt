
// Message representa uma mensagem na fila, contendo um conteúdo e um tópico.
data class Message(
    val content: String,
    val topic: String
)