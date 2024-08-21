package model.utils

enum class GMT(private val s: String) {
    GMT_5("GMT-5"),
    GMT_4("GMT-4"),
    GMT_3("GMT-3"),
    GMT_2("GMT-2"),
    GMT_1("GMT-1"),
    GMT0("GMT"),
    GMT1("GMT+1"),
    GMT2("GMT+2"),
    GMT3("GMT+3"),
    GMT4("GMT+4"),
    GMT5("GMT+5");

    fun getString(): String {
        return s
    }
}