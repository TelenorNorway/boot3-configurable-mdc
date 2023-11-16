package app

import no.telenor.fetch.LocalTestRestTemplate
import no.telenor.fetch.TestFetch
import no.telenor.fetch.fetch
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.Test

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TestersenApplicationTests : TestFetch {

	// note: IntelliJ beef, suppress is needed for some reason...
	@Suppress("unused")
	@Autowired
	override lateinit var testRestTemplate: LocalTestRestTemplate

	@Test
	fun `get mdc values`() {
		val mdc = fetch<Map<String, String?>>("/").body
		println(mdc)
	}
}
