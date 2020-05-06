package etu.vt.trpo_backend.trpo_backend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TrpoBackendApplication

fun main(args: Array<String>) {
	runApplication<TrpoBackendApplication>(*args)
}
