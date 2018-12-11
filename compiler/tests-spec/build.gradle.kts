plugins {
    kotlin("jvm")
    id("jps-compatible")
}

dependencies {
    testCompile(projectTests(":compiler"))
    compileOnly("org.jetbrains:annotations:13.0")
}

sourceSets {
    "main" { }
    "test" { projectDefault() }
}

projectTest {
    workingDir = rootDir
}

val generateSpecTests by generator("org.jetbrains.kotlin.spec.tasks.GenerateSpecTestsKt")

val generateFeatureInteractionSpecTestData by generator("org.jetbrains.kotlin.spec.tasks.GenerateFeatureInteractionSpecTestDataKt")

val printSpecTestsStatistic by generator("org.jetbrains.kotlin.spec.tasks.PrintSpecTestsStatisticKt")

val generateJsonTestsMap by generator("org.jetbrains.kotlin.spec.tasks.GenerateJsonTestsMapKt")

val remoteRunTests by task<Test> {
    val packagePrefix = "org.jetbrains.kotlin."
    val includeTests = setOf(
        "checkers.DiagnosticsTestSpecGenerated\$NotLinked\$Contracts*",
        "checkers.DiagnosticsTestSpecGenerated\$NotLinked\$Annotations\$Type_annotations*",
        "codegen.BlackBoxCodegenTestSpecGenerated\$NotLinked\$Annotations\$Type_annotations*",
        "codegen.BlackBoxCodegenTestSpecGenerated\$NotLinked\$Objects\$Inheritance*"
    )

    workingDir = rootDir

    filter {
        includeTests.forEach { includeTestsMatching(packagePrefix + it) }
    }
}
