package org.openrepose.gradle.plugins.codacy

import com.codacy.CodacyCoverageReporter
import com.codacy.configuration.parser.BaseCommandConfig
import com.codacy.configuration.parser.Final
import org.gradle.api.tasks.TaskAction
import scala.Option

class CodacyFinalizeTask : CodacyBaseTask() {
    @TaskAction
    fun finalizeReport() {
        CodacyCoverageReporter.run(Final(BaseCommandConfig(Option.apply(pluginExtension?.projectToken), Option.apply(pluginExtension?.apiUrl), Option.empty(), Option.empty())))
    }
}