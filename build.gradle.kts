import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.0.0"
    id("io.spring.dependency-management") version "1.1.0"
    kotlin("jvm") version "1.7.21"
    kotlin("plugin.spring") version "1.7.21"
}

group = "com.khanivorous"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.swagger.core.v3:swagger-annotations:2.2.7")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.0.0")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("app.getxray:xray-junit-extensions:0.6.2")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.test {
    useJUnitPlatform() {
        excludeTags ("Integration")
    }
    testLogging {
        events ("started", "skipped", "passed", "failed", "STANDARD_OUT", "STANDARD_ERROR")
        showStackTraces = true
        exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
        debug {
            events ("started", "skipped", "passed", "failed", "STANDARD_OUT", "STANDARD_ERROR")
        }
    }
}

tasks.register<Test>("integrationTests") {
    useJUnitPlatform() {
        includeTags ("Integration")
        excludeTags ("Application")
    }
    testLogging {
        events ("started", "skipped", "passed", "failed", "STANDARD_OUT", "STANDARD_ERROR")
        showStackTraces = true
        exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
        debug {
            events ("started", "skipped", "passed", "failed", "STANDARD_OUT", "STANDARD_ERROR")
        }
    }

}

tasks.register<Test>("applicationTests") {
    useJUnitPlatform() {
        includeTags ("Application")
        excludeTags ("Integration")
    }
    testLogging {
        events ("started", "skipped", "passed", "failed", "STANDARD_OUT", "STANDARD_ERROR")
        showStackTraces = true
        exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
        debug {
            events ("started", "skipped", "passed", "failed", "STANDARD_OUT", "STANDARD_ERROR")
        }
    }
}
