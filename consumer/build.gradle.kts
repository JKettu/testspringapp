import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.2.4.RELEASE"
    id("io.spring.dependency-management") version "1.0.9.RELEASE"
    kotlin("jvm") version "1.3.61"
    kotlin("plugin.spring") version "1.3.61"
}

group = "ru.rti.kettu"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
    implementation("io.github.openfeign:feign-jackson:10.7.4")
    implementation("org.springframework.cloud:spring-cloud-openfeign-core:2.2.1.RELEASE")
    implementation ("org.springframework.cloud:spring-cloud-starter-netflix-hystrix:2.2.1.RELEASE")
    implementation("io.github.openfeign:feign-slf4j:9.3.1")
    implementation("org.springframework.integration:spring-integration-core:4.0.4.RELEASE")
    implementation("wsdl4j:wsdl4j:1.6.1")
    implementation("io.github.openfeign:feign-soap:10.7.4")
    implementation("org.projectlombok:lombok:1.18.10")


}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}

apply(from = "task.gradle")
//apply(from = "importToMaven.gradle")