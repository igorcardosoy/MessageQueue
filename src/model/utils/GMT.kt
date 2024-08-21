package model.utils

enum class GMT(val s: String) {
    GMT_5("model.Utils.GMT-5"),
    GMT_4("model.Utils.GMT-4"),
    GMT_3("model.Utils.GMT-3"),
    GMT_2("model.Utils.GMT-2"),
    GMT_1("model.Utils.GMT-1"),
    GMT0("model.Utils.GMT"),
    GMT1("model.Utils.GMT+1"),
    GMT2("model.Utils.GMT+2"),
    GMT3("model.Utils.GMT+3"),
    GMT4("model.Utils.GMT+4"),
    GMT5("model.Utils.GMT+5");

    fun getString(): String {
        return s
    }
}