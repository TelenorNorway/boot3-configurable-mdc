package no.telenor.kt.boot3.configurable.mdc

data class MdcKeyConfiguration(
	val key: String,
	var enabled: Boolean,
	val valueCollector: MdcKeyValueCollector,
)
