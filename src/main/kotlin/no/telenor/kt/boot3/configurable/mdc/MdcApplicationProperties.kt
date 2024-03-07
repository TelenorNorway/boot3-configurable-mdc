package no.telenor.kt.boot3.configurable.mdc

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@ConfigurationProperties(prefix = "logging")
internal data class MdcApplicationProperties(val map: Map<String, *>)
