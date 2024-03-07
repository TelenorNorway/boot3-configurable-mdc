plugins {
	kotlin("jvm") version "1.9.22"
	kotlin("plugin.spring") version "1.9.22"
	id("org.springframework.boot") version "3.2.3"
	id("io.spring.dependency-management") version "1.1.4"
	id("sh.tnn") version "0.2.0"
	`maven-publish`
}

group = "no.telenor.kt"
version = versioning.environment()

repositories {
	mavenCentral()
	telenor.public()
}

dependencies {
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

kotlin.jvmToolchain(17)

publishing {
	repositories.github.actions()
	publications.register<MavenPublication>("gpr") {
		from(components["kotlin"])
	}
}

tasks.named("bootJar") {
	enabled = false
}
