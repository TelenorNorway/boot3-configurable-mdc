package no.telenor.kt.boot3.configurable.mdc

import org.springframework.context.annotation.Configuration
import org.springframework.expression.spel.SpelCompilerMode
import org.springframework.expression.spel.SpelParserConfiguration
import org.springframework.expression.spel.standard.SpelExpressionParser

@Configuration
class MdcApplicationPropertiesProcessor internal constructor(
	keyRegistry: KeyRegistry,
	mdcApplicationProperties: MdcApplicationProperties,
) {
	private val parser =
		SpelExpressionParser(SpelParserConfiguration(SpelCompilerMode.IMMEDIATE, (object {})::class.java.classLoader))

	init {
		for ((key, value) in getKeysOf(mdcApplicationProperties.map)) {
			when (value) {
				is Boolean -> keyRegistry.setEnabledStateFor(key, value)
				is String -> {
					val expr = try {
						parser.parseExpression(value)
					} catch (ex: Throwable) {
						throw Exception("Could not parse Spring expression for logging.map.$key", ex)
					}
					keyRegistry.define(key) { expr.getValue(it)?.toString() }
				}

				else -> throw Exception("Invalid class type for application property logging.map.$key, expected string og boolean (got ${value?.let { it::class.java } ?: "null"})")
			}
		}
	}
}

private fun process(
	seen: MutableMap<String, Any?> = mutableMapOf(),
	newKey: String,
	value: Any?,
) {
	when (value) {
		is Map<*, *> -> {
			@Suppress("UNCHECKED_CAST")
			getKeysOf(
				value as Map<String, Any?>,
				seen,
				"$newKey."
			)
		}

		is List<*> -> {
			getKeysOf(value, seen, "$newKey.")
		}

		else -> {
			seen[newKey] = value
		}
	}
}

private fun throwDuplicateError(newKey: String): Nothing =
	throw Exception("Duplicate application property of logging.map.$newKey")

private fun getKeysOf(
	properties: Map<String, Any?>,
	seen: MutableMap<String, Any?> = mutableMapOf(),
	parent: String = ""
): Map<String, Any?> {
	for ((key, value) in properties) {
		val newKey = "$parent$key"
		if (seen.containsKey(newKey)) throwDuplicateError(newKey)
		process(seen, newKey, value)
	}
	return seen
}

private fun getKeysOf(
	properties: List<Any?>,
	seen: MutableMap<String, Any?>,
	parent: String,
): Map<String, Any?> {
	for (index in properties.indices) {
		val newKey = "$parent$index"
		val value = properties[index]
		if (seen.containsKey(newKey)) throwDuplicateError(newKey)
		process(seen, newKey, value)
	}
	return seen
}
