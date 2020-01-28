package ru.rti.kettu.sbkotlin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringBootSOAPClientApplication

fun main(args: Array<String>) {
    runApplication<SpringBootSOAPClientApplication>(*args)
}
