enum class Colors(val s: String) {
    RESET ("\u001B[0m"),
    ANSI_PURPLE("\u001B[35m"),
    RED("\u001B[31m"),
    ANSI_RED_BACKGROUND("\u001B[41m"),
    GREEN("\u001B[32m");

    fun getString(): String {
        return s
    }
}