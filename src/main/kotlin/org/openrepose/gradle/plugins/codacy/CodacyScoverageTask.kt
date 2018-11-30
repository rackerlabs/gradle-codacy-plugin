package org.openrepose.gradle.plugins.codacy

class CodacyScoverageTask : CodacyCoverageTask() {
    override val language: String = "scala"
}
