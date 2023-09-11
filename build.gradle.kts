import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {

	id("org.springframework.boot") version "2.7.5"
	id("io.spring.dependency-management") version "1.0.15.RELEASE"
	kotlin("jvm") version "1.7.20"
	kotlin("plugin.spring") version "1.7.20"
	kotlin("plugin.jpa") version "1.7.20"
	kotlin("kapt") version "1.7.20"
	id("nu.studer.jooq") version "7.1.1"
}

group = "com.library"
version = "0.0.1"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

extra["xc_version"] = "$version"
extra["springCloudVersion"] = "2021.0.4"

tasks.jar {
	enabled = false
}

dependencies {
//springdoc 관련 부분
	implementation("org.springdoc:springdoc-openapi-webflux-ui:1.6.13")
	implementation("org.springdoc:springdoc-openapi-kotlin:1.6.13")
	implementation("io.netty:netty-resolver-dns-native-macos:4.1.85.Final:osx-aarch_64")

	//implementation("org.springframework.boot:spring-boot-starter-data-mongodb")

	implementation("org.jooq:jooq:3.16.7")
	implementation("org.jooq:jooq-codegen:3.16.7")
	implementation("org.jooq:jooq-meta:3.16.7")
	implementation("org.jooq:jooq-kotlin:3.16.7")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-jooq") {
		exclude(group = "org.jooq")
	}
	implementation("org.springframework.boot:spring-boot-starter-undertow")

	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
	implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")

	implementation("com.playtika.reactivefeign:feign-reactor-spring-cloud-starter:3.2.6")
	implementation("org.springframework.cloud:spring-cloud-starter-sleuth")
	implementation("org.springframework.cloud:spring-cloud-starter-loadbalancer")

	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

	implementation("org.liquibase:liquibase-core")

	implementation("com.ninja-squad:springmockk:3.1.2")
	implementation("org.springframework.boot:spring-boot-starter-test"){
		exclude(module = "mokito-core")
	}

	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

	implementation("io.r2dbc:r2dbc-pool")

	runtimeOnly("org.postgresql:postgresql")
	runtimeOnly("org.postgresql:r2dbc-postgresql")

	compileOnly("org.projectlombok:lombok")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	annotationProcessor("org.projectlombok:lombok")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.projectreactor:reactor-test")

	jooqGenerator("org.postgresql:postgresql:42.3.8")
	jooqGenerator("jakarta.xml.bind:jakarta.xml.bind-api:4.0.0")
}
dependencyManagement {
	imports {
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
	}
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

jooq {
	version.set("3.16.7")
	edition.set(nu.studer.gradle.jooq.JooqEdition.OSS)
	configurations {
		create("main") {
			jooqConfiguration.apply {
				logging = org.jooq.meta.jaxb.Logging.WARN
				jdbc.apply {
					driver = "org.postgresql.Driver"
					url = "jdbc:postgresql://localhost:5432/bookrental"
					user = "postgres"
					password = "081725"
				}
				generator.apply {
					name = "org.jooq.codegen.KotlinGenerator"
					database.apply {
						name = "org.jooq.meta.postgres.PostgresDatabase"
						inputSchema = "book_rental"
						includes = ".*"
						excludes = """ databasechange.* """
						forcedTypes.addAll(
							arrayOf(
								org.jooq.meta.jaxb.ForcedType()
									.withName("varchar")
									.withIncludeExpression(".*")
									.withIncludeTypes("JSONB?"),
								org.jooq.meta.jaxb.ForcedType()
									.withName("varchar")
									.withIncludeExpression(".*")
									.withIncludeTypes("INET")
							).toList()
						)
					}
					generate.apply {
						isJavaTimeTypes = true
						isDeprecated = false
						isRecords = false
						isImmutablePojos = false
						isFluentSetters = false
					}
					target.apply {
						packageName = "com.group.book_rental_application.adapters.infrastructure.database.jooq.generated"
						directory = "src/main/java"
					}
					strategy.name = "org.jooq.codegen.DefaultGeneratorStrategy"
				}
			}
		}
	}
}

