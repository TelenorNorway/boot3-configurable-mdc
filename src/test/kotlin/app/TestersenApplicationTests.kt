package app

import no.telenor.fetch.LocalTestRestTemplate
import no.telenor.fetch.TestFetch
import no.telenor.fetch.fetch
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.Test

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TestersenApplicationTests : TestFetch {

	@Autowired
	override lateinit var testRestTemplate: LocalTestRestTemplate

	@Test
	fun `get mdc values`() {
		@Suppress("UNCHECKED_CAST")
		val mdc = fetch<Map<String, String?>>("/").body
		println(mdc)
	}
}
