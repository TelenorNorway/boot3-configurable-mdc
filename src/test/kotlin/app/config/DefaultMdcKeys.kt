package app.config

import jakarta.servlet.http.HttpServletRequest
import no.telenor.kt.boot3.configurable.mdc.KeyRegistry
import no.telenor.kt.boot3.configurable.mdc.MdcApplicationPropertiesProcessor
import org.springframework.boot.autoconfigure.AutoConfigureBefore
import org.springframework.context.annotation.Configuration

@Configuration
@AutoConfigureBefore(MdcApplicationPropertiesProcessor::class)
class DefaultMdcKeys(keyRegistry: KeyRegistry) {
	init {
		keyRegistry.define("req.userAgent", false) {
			(it["request"] as? HttpServletRequest)?.getHeader("user-agent")
		}
	}
}
