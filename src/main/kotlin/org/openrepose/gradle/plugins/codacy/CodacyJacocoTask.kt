package org.openrepose.gradle.plugins.codacy

class CodacyJacocoTask : CodacyCoverageTask() {
    override val language: String = "java"
}
