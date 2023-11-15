package app.config

import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ControllerAdvice {
	val log = LoggerFactory.getLogger(javaClass)!!

	@Suppress("unused")
	@ExceptionHandler(Exception::class)
	fun advice(ex: Exception): ResponseEntity<String> {
		log.info("Something went wrong hello", ex)
		return ResponseEntity.internalServerError().body("Something went wrong!")
	}
}
