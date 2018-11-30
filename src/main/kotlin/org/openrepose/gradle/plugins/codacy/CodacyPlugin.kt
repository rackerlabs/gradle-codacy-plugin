package org.openrepose.gradle.plugins.codacy

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.testing.jacoco.plugins.JacocoTaskExtension
import org.scoverage.ScoverageExtension
import java.io.File

class CodacyPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val codacyPluginExtension = project.extensions.create("codacy", CodacyPluginExtension::class.java, project)
        //task to finish the report
        val codacyFinalizeTask = project.tasks.create("sendCodacyReport", CodacyFinalizeTask::class.java)
        codacyFinalizeTask.pluginExtension = codacyPluginExtension
        //look through the projects for jacoco and scoverage tasks and add a report task
        project.allprojects
                .filter { subproject -> subproject.tasks.findByName("jacocoTestReport") != null }
                .forEach { subproject -> buildJacocoTask(subproject, codacyFinalizeTask, codacyPluginExtension) }
        project.allprojects
                .filter { subproject -> subproject.tasks.findByName("reportScoverage") != null }
                .forEach { subproject -> buildScoverageTask(subproject, codacyFinalizeTask, codacyPluginExtension) }
    }

    private fun buildJacocoTask(subproject: Project, finalizeTask: CodacyFinalizeTask, extension: CodacyPluginExtension) {
        val jacocoTask = subproject.tasks.create("sendCodacyJacocoReport", CodacyJacocoTask::class.java)
        finalizeTask.dependsOn(jacocoTask)
        jacocoTask.pluginExtension = extension
        jacocoTask.outputFile = subproject.extensions.getByType(JacocoTaskExtension::class.java).destinationFile
        subproject.tasks.findByName("jacocoTestReport")?.dependsOn(jacocoTask)
    }

    private fun buildScoverageTask(subproject: Project, finalizeTask: CodacyFinalizeTask, extension: CodacyPluginExtension) {
        val scoverageTask = subproject.tasks.create("sendCodacyScoverageReport", CodacyScoverageTask::class.java)
        finalizeTask.dependsOn(scoverageTask)
        scoverageTask.pluginExtension = extension
        scoverageTask.outputFile = File(subproject.extensions.getByType(ScoverageExtension::class.java).reportDir, "scoverage.coverage.xml")
        subproject.tasks.findByName("reportScoverage")?.dependsOn(scoverageTask)
    }
}
