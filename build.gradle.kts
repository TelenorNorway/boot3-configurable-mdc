plugins {
	kotlin("jvm") version "1.9.23"
	kotlin("plugin.spring") version "1.9.23"
	kotlin("kapt") version "1.9.23"
	id("org.springframework.boot") version "3.2.4"
	id("io.spring.dependency-management") version "1.1.4"
	id("sh.tnn") version "0.3.0"
	`maven-publish`
}

group = "no.telenor.kt"

repositories {
	mavenCentral()
	telenor.public()
}

dependencies {
	kapt("org.springframework.boot:spring-boot-configuration-processor:3.2.4")
	api("no.telenor.kt:mdc-transaction:0.2.+")
	compileOnly("org.springframework.boot:spring-boot-starter-web")
	testImplementation(kotlin("test"))
	testImplementation(kotlin("reflect"))
	testImplementation(kotlin("stdlib"))
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.boot:spring-boot-starter-web")
	testImplementation("org.springframework.boot:spring-boot-starter-logging")
	testImplementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	testImplementation("no.telenor.fetch:test-fetch:0.1.0")
}

tasks.test {
	useJUnitPlatform()
}

kotlin.jvmToolchain(21)

publishing {
	repositories.github.actions()
	publications.register<MavenPublication>("gpr") {
		from(components["kotlin"])
	}
}

tasks.named("bootJar") {
	enabled = false
}
