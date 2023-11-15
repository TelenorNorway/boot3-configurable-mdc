package no.telenor.kt.boot3.configurable.mdc

import org.springframework.stereotype.Component

@Component
class KeyRegistry {
	val keys = mutableMapOf<String, MdcKeyConfiguration>()

	fun setEnabledStateFor(key: String, state: Boolean) {
		keys[key]?.enabled = state
	}

	fun define(key: String, enabled: Boolean, block: MdcKeyValueCollector) {
		keys[key] = MdcKeyConfiguration(key, enabled, block)
	}

	fun define(key: String, block: MdcKeyValueCollector) = define(key, true, block)
}
