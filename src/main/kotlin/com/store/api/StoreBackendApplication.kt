package com.store.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class StoreBackendApplication

fun main(args: Array<String>) {
	runApplication<com.store.api.StoreBackendApplication>(*args)
}
