package org.openrepose.gradle.plugins.codacy

import org.gradle.api.DefaultTask

open class CodacyBaseTask : DefaultTask() {
    var pluginExtension: CodacyPluginExtension? = null
}
