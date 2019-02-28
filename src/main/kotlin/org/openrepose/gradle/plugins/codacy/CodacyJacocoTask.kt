package org.openrepose.gradle.plugins.codacy

open class CodacyJacocoTask : CodacyCoverageTask() {
    override val language: String = "Java"
}
