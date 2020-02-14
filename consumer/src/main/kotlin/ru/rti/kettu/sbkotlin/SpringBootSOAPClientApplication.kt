package ru.rti.kettu.sbkotlin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker
import org.springframework.cloud.netflix.hystrix.EnableHystrix
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.integration.config.EnableIntegration

@SpringBootApplication
@EnableFeignClients
@EnableHystrix
@EnableIntegration
class SpringBootSOAPClientApplication

fun main(args: Array<String>) {
    runApplication<SpringBootSOAPClientApplication>(*args)
}