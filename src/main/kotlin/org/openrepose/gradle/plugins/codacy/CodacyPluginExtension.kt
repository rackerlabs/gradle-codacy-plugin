package org.openrepose.gradle.plugins.codacy

import org.gradle.api.Project

class CodacyPluginExtension(project: Project) {
    var projectToken: String? = project.findProperty("CODACY_PROJECT_TOKEN") as String
    var apiUrl: String? = null
}