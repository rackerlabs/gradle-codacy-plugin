package org.openrepose.gradle.plugins.codacy

import com.codacy.configuration.parser.BaseCommandConfig
import com.codacy.configuration.parser.Report
import com.codacy.di.Components
import com.codacy.model.configuration.ReportConfig
import org.gradle.api.GradleException
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.TaskAction
import scala.Option
import scala.runtime.BoxedUnit
import java.io.File

abstract class CodacyCoverageTask : CodacyBaseTask() {
    @InputFile
    var outputFile: File? = null

    abstract val language: String

    @TaskAction
    fun sendCoverage() {
        val components = Components(Report(
                BaseCommandConfig(Option.apply(pluginExtension?.projectToken), Option.apply(pluginExtension?.apiUrl), Option.empty(), Option.empty()),
                language,
                Option.empty(),
                outputFile,
                Option.apply(BoxedUnit.UNIT),
                Option.empty()))
        val validatedConfig = components.validatedConfig()
        val result = components.reportRules().codacyCoverage(validatedConfig as ReportConfig)
        if(result.isLeft) {
            throw GradleException("Sending the Codacy report Failed: ${result.left().get()}")
        }
    }
}
