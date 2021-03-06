plugins {
    kotlin("jvm") version "1.5.0"
    kotlin("plugin.serialization") version "1.5.0"
    application
}

repositories {
    jcenter()
    maven(url = "https://jitpack.io")
    maven(url = "https://m2.dv8tion.net/releases")
}

val exposedVersion:String = "0.29.1"
val logbackVersion: String = "0.9.26"

dependencies {
    //The discord related libraries
    implementation("net.dv8tion:JDA:4.2.1_255")
    implementation("com.sedmelluq:lavaplayer:1.3.75")

    //Expsed
    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")

    //logback
    implementation("ch.qos.logback:logback-classic:$logbackVersion")

    //other
    implementation("org.mariadb.jdbc:mariadb-java-client:2.1.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.2")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.0.1")
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("com.squareup.okhttp3:okhttp:4.9.1")
    implementation("com.google.code.gson:gson:2.8.6")
    implementation("com.charleskorn.kaml:kaml:0.30.0")
    implementation("junit:junit:4.13.1")
    implementation("junit:junit:4.13.1")
    implementation("com.oracle.ojdbc:ojdbc8:19.3.0.0") {
        exclude(module = "simplefan")
        exclude(module = "ons")
    }
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
}

application {
    // Define the main class for the application.
    mainClassName = "com.github.yeoj34760.spuppy.bot.Bot"
}