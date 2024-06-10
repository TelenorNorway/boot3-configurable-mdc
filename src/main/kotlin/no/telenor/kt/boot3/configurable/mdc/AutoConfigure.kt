package no.telenor.kt.boot3.configurable.mdc

import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Import

@AutoConfiguration
@Import(
	MdcRequestContextFilter::class,
	KeyRegistry::class,
	MdcApplicationPropertiesProcessor::class,
	MdcAppenderFilter::class,
)
@EnableConfigurationProperties(
	MdcApplicationProperties::class,
)
internal class AutoConfigure
