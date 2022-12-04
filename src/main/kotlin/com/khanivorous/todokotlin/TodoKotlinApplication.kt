package com.khanivorous.todokotlin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TodoKotlinApplication

fun main(args: Array<String>) {
    runApplication<TodoKotlinApplication>(*args)
}
