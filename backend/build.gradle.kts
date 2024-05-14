plugins {
    java
    id("org.springframework.boot") version "3.2.5"
    id("io.spring.dependency-management") version "1.1.4"
    id("com.diffplug.spotless") version "6.25.0"
}

group = "com.rikishi"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

spotless {
  format("misc", {
    target("*.gradle.kts", ".gitattributes", ".gitignore")

    trimTrailingWhitespace()
    indentWithSpaces()
    endWithNewline()
  })
  java {
    // use the default importOrder configuration
    importOrder()
    removeUnusedImports()
    // cleanthat will perform some refactors
    cleanthat()
    // apply a specific flavor of google-java-format
    googleJavaFormat("1.22.0").aosp().reflowLongStrings().skipJavadocFormatting()
    // fix formatting of type annotations
    formatAnnotations()
  }
}
