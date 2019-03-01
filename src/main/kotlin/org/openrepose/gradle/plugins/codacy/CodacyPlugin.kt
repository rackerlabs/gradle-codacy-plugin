package org.openrepose.gradle.plugins.codacy

import org.apache.commons.lang3.StringUtils
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.testing.jacoco.tasks.JacocoReport
import org.scoverage.ScoverageExtension
import org.scoverage.ScoverageReport
import java.io.File

class CodacyPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val codacyPluginExtension = project.extensions.create("codacy", CodacyPluginExtension::class.java, project)
        //task to finish the report
        val codacyFinalizeTask = project.tasks.create("sendCodacyReport", CodacyFinalizeTask::class.java)
        codacyFinalizeTask.pluginExtension = codacyPluginExtension
        //look through the projects for jacoco and scoverage tasks and add a report task
        project.allprojects.forEach { subproject -> buildJacocoTask(subproject, codacyFinalizeTask, codacyPluginExtension) }
        project.allprojects.forEach { subproject -> buildScoverageTask(subproject, codacyFinalizeTask, codacyPluginExtension) }
    }

    private fun buildJacocoTask(subproject: Project, finalizeTask: CodacyFinalizeTask, extension: CodacyPluginExtension) {
        subproject.tasks.withType(JacocoReport::class.java) { reportTask ->
            val jacocoTask = subproject.tasks.create("sendCodacy${StringUtils.capitalize(reportTask.getName())}", CodacyJacocoTask::class.java)
            finalizeTask.dependsOn(jacocoTask)
            jacocoTask.pluginExtension = extension
            jacocoTask.outputFile = reportTask.reports.xml.destination
            jacocoTask.dependsOn(reportTask)
        }
    }

    private fun buildScoverageTask(subproject: Project, finalizeTask: CodacyFinalizeTask, extension: CodacyPluginExtension) {
        subproject.tasks.withType(ScoverageReport::class.java) { reportTask ->
            val scoverageTask = subproject.tasks.create("sendCodacy${StringUtils.capitalize(reportTask.getName())}", CodacyScoverageTask::class.java)
            finalizeTask.dependsOn(scoverageTask)
            scoverageTask.pluginExtension = extension
            scoverageTask.outputFile = File(reportTask.args[2], "cobertura.xml")
            scoverageTask.dependsOn(reportTask)
        }
    }
}
