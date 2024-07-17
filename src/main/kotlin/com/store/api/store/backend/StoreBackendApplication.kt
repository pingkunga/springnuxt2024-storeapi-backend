package com.store.api.store.backend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class StoreBackendApplication

fun main(args: Array<String>) {
	runApplication<StoreBackendApplication>(*args)
}
