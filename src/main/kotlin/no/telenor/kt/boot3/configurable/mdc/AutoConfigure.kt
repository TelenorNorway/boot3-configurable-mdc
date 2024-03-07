package no.telenor.kt.boot3.configurable.mdc

import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.ComponentScan

@AutoConfiguration
@ComponentScan
@EnableConfigurationProperties(
	MdcApplicationProperties::class,
)
internal class AutoConfigure
