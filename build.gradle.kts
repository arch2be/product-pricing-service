plugins {
	kotlin("jvm") version "1.9.25"
	kotlin("plugin.spring") version "1.9.25"
	id("org.springframework.boot") version "3.4.0"
	id("io.spring.dependency-management") version "1.1.6"
}

group = "io.github.arch2be"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.exposed:exposed-spring-boot-starter:0.48.0")

	testImplementation("org.springframework.boot:spring-boot-starter-test")

	testImplementation("io.kotest:kotest-runner-junit5-jvm:5.9.1")
	testImplementation("io.kotest:kotest-assertions-core:5.9.1")
	testImplementation("io.kotest.extensions:kotest-extensions-testcontainers:2.0.2")
	testImplementation("io.mockk:mockk:1.13.13")

	testImplementation("org.testcontainers:testcontainers")
	testImplementation("org.testcontainers:postgresql")

	runtimeOnly("org.postgresql:postgresql")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()

	testLogging {
		showExceptions = true
		showCauses = true
		showStackTraces = true
	}
}
