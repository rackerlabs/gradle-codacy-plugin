package org.openrepose.gradle.plugins.codacy

import com.codacy.configuration.parser.BaseCommandConfig
import com.codacy.configuration.parser.Final
import com.codacy.di.Components
import com.codacy.model.configuration.FinalConfig
import org.gradle.api.GradleException
import org.gradle.api.tasks.TaskAction
import scala.Option

open class CodacyFinalizeTask : CodacyBaseTask() {
    @TaskAction
    fun finalizeReport() {
        val components = Components(Final(BaseCommandConfig(Option.apply(pluginExtension?.projectToken), Option.apply(pluginExtension?.apiUrl), Option.empty(), Option.empty())))
        val validatedConfig = components.validatedConfig()
        val result = components.reportRules().finalReport(validatedConfig as FinalConfig)
        if(result.isLeft) {
            throw GradleException("Finalizing the Codacy report Failed: ${result.left().get()}")
        }
    }
}
