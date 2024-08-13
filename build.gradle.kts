plugins {
    kotlin("jvm") version "2.0.0"
}

group = "com.nickcoblentz.sftp"
version = "0.1"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("com.github.mwiede:jsch:0.2.19")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}