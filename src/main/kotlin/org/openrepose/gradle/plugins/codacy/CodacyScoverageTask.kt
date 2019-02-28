package org.openrepose.gradle.plugins.codacy

open class CodacyScoverageTask : CodacyCoverageTask() {
    override val language: String = "Scala"
}
