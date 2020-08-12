package me.wonsang.springbatchservice

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class SpringBatchServiceApplication

fun main(args: Array<String>) {
    //runApplication<SpringBatchServiceApplication>(*args)
    System.exit(SpringApplication.exit(SpringApplication.run(SpringBatchServiceApplication::class.java, *args)))

}
