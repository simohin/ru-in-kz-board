package com.github.simohin.board

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class BoardApplication

fun main(args: Array<String>) {
    runApplication<BoardApplication>(*args)
}
