import kotlin.concurrent.thread

fun main() {
    val name = "Kotlin"
    val thread = thread( start = false ) {
        println("Hello, $name!")
    }

    thread.start()
}