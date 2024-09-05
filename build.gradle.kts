plugins {
    java
    id("org.springframework.boot") version "3.3.3"
    id("io.spring.dependency-management") version "1.1.6"

    // Lombok
    id("io.freefair.lombok") version "6.2.0"

}

group = "moka"
version = "1.0.0"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("nz.net.ultraq.thymeleaf:thymeleaf-layout-dialect")
    implementation("org.springframework.boot:spring-boot-starter-web")
    // Data Validation
    implementation("org.springframework.boot:spring-boot-starter-validation")

    /**
     * markdown view
     * */
    implementation("org.commonmark:commonmark:0.22.0")

    // commonmark-ext-gfm-tables
    implementation("org.commonmark:commonmark-ext-gfm-tables:0.22.0")

    /**
     * annotationProcessor
     * */
    // https://www.baeldung.com/intellij-resolve-spring-boot-configuration-properties
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    /**
     * developmentOnly
     * */
    developmentOnly("org.springframework.boot:spring-boot-devtools")

    /**
     * Test
     * */
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("org.projectlombok:lombok")
    testAnnotationProcessor("org.projectlombok:lombok")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
