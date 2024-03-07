package no.telenor.kt.boot3.configurable.mdc

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "logging")
internal data class MdcApplicationProperties(val map: Map<String, *>)
