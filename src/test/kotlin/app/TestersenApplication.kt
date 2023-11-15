package app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TestersenApplication

fun main(args: Array<String>) {
	runApplication<TestersenApplication>(*args)
}
