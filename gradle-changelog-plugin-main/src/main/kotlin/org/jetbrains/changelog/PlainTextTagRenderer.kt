package org.jetbrains.changelog

import org.intellij.markdown.ast.ASTNode
import org.intellij.markdown.html.HtmlGenerator

class PlainTextTagRenderer : HtmlGenerator.TagRenderer {

    override fun openTag(
        node: ASTNode,
        tagName: CharSequence,
        vararg attributes: CharSequence?,
        autoClose: Boolean
    ) = ""

    override fun closeTag(tagName: CharSequence) = ""

    override fun printHtml(html: CharSequence) = html
}
