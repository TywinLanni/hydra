package com.github.tywinlanni.hydra

fun main() {
    val mans = generateSequence {
        "つ ◕_◕ ༽つ".let {
            if ((0..1).random() == 0) {
                it
            } else {
                it.reversed()
            }
        }
    }

    println(
        mans.take(10)
            .joinToString(separator = "\n")
    )
}
