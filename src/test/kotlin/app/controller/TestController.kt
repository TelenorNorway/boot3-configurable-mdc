package app.controller

import org.slf4j.LoggerFactory
import org.slf4j.MDC
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController {
	private val log = LoggerFactory.getLogger(javaClass)!!

	@Suppress("unused")
	@GetMapping
	fun getMdcValues(): Map<String, String?> = MDC.getCopyOfContextMap() ?: emptyMap()

	@Suppress("unused")
	@GetMapping("/test")
	fun test() {
		log.info("Hello from test")
		throw Exception("hello")
	}
}
