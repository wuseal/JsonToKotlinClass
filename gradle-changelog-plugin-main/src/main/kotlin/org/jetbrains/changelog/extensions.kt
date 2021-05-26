package org.jetbrains.changelog

import groovy.lang.Closure
import org.intellij.markdown.html.HtmlGenerator
import org.intellij.markdown.parser.MarkdownParser
import org.jetbrains.changelog.flavours.ChangelogFlavourDescriptor
import org.jetbrains.changelog.flavours.PlainTextFlavourDescriptor
import java.text.SimpleDateFormat
import java.util.Date

fun <T : Any> closure(function: () -> T) = object : Closure<T>(null) {
    @Suppress("unused")
    fun doCall() = function()
}

fun date(pattern: String = "yyyy-MM-dd") = SimpleDateFormat(pattern).format(Date())!!

fun markdownToHTML(input: String) = ChangelogFlavourDescriptor().run {
    HtmlGenerator(input, MarkdownParser(this).buildMarkdownTreeFromString(input), this, false)
        .generateHtml()
}

fun markdownToPlainText(input: String) = PlainTextFlavourDescriptor().run {
    HtmlGenerator(input, MarkdownParser(this).buildMarkdownTreeFromString(input), this, false)
        .generateHtml(PlainTextTagRenderer())
}
