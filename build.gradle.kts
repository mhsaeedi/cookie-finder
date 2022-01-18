import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	application
	kotlin("jvm") version "1.5.10"
}
application {
	mainClass.set("ActiveCookieAppKt")
}

group = "com.quantcast"
version = "1.0"

repositories {
	mavenCentral()
}

dependencies {
	implementation("info.picocli:picocli:4.6.2")
	implementation("ch.qos.logback:logback-classic:1.2.10")
	implementation("io.github.microutils:kotlin-logging-jvm:2.1.21")
	testImplementation("io.mockk:mockk:1.12.2")
	testImplementation(kotlin("test"))
}

tasks.test {
	useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
	kotlinOptions.jvmTarget = "1.8"
}

tasks {
	val fatJar = register<Jar>("fatJar") {
		dependsOn.addAll(listOf("compileJava", "compileKotlin", "processResources")) // We need this for Gradle optimization to work
		archiveClassifier.set("standalone") // Naming the jar
		duplicatesStrategy = DuplicatesStrategy.EXCLUDE
		manifest { attributes(mapOf("Main-Class" to application.mainClass)) } // Provided we set it up in the application plugin configuration
		val sourcesMain = sourceSets.main.get()
		val contents = configurations.runtimeClasspath.get()
			.map { if (it.isDirectory) it else zipTree(it) } +
				sourcesMain.output
		from(contents)
	}
	build {
		dependsOn(fatJar) // Trigger fat jar creation during build
	}
}
