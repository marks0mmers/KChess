plugins {
    alias(libs.plugins.kotlin)
    alias(libs.plugins.compose)
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(compose.desktop.currentOs)
}

compose.desktop {
    application {
        mainClass = "com.chess.ChessKt"
    }
}

kotlin {
    jvmToolchain(14)
}
