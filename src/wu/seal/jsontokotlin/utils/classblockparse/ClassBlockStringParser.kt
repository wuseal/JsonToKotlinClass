package wu.seal.jsontokotlin.utils.classblockparse

import wu.seal.jsontokotlin.utils.getClassNameFromClassBlockString

/**
 * parser for parse class block string, with this util, we could get the class struct elements
 */
class ClassBlockStringParser(private val classBlockString: String) {

    fun getKotlinDataClass() :KotlinDataClass{
        return KotlinDataClass(getClassAnnotations(), getClassName(), getProperties())
    }

    fun getClassName(): String {
        return getClassNameFromClassBlockString(classBlockString)
    }

    fun getClassAnnotations(): List<String> {
        val annotationsBlock = classBlockString.substringBefore("data class").trim()
        return annotationsBlock.split("\n").filter { it.contains("@") }.map { it.trim() }
    }

    fun getProperties(): List<KotlinDataClass.Property> {
        val propertiesBlock = classBlockString.substringAfter("(").substringBeforeLast(")").trim()

        val lines = propertiesBlock.split("\n")

        val properties = mutableListOf<KotlinDataClass.Property>()

        val propertyLinesList = getPropertyLinesList(lines)

        propertyLinesList.forEachIndexed { index, propertyBlockLines ->
            val annotations = getPropertyAnnotations(propertyBlockLines)
            val propertyKeyword = getPropertyKeyword(propertyBlockLines.last())
            val propertyName = getPropertyName(propertyBlockLines.last())
            val isLastLine = index == propertyLinesList.lastIndex
            val propertyType = getPropertyType(propertyBlockLines.last(),isLastLine)
            val propertyValue = getPropertyValue(propertyBlockLines.last(), isLastLine)
            val propertyComment = getPropertyComment(propertyBlockLines.last())
            properties.add(
                KotlinDataClass.Property(
                    annotations,
                    propertyKeyword,
                    propertyName,
                    propertyType,
                    propertyValue,
                    propertyComment,
                    isLastLine
                )
            )
        }
        return properties
    }

    private fun getPropertyLinesList(lines: List<String>): List<List<String>> {
        val propertyLinesList = mutableListOf<List<String>>()
        var propertyLines = mutableListOf<String>()
        lines.forEach {
            if ((it.contains("val") || it.contains("var")) && (it.contains(":"))) {
                propertyLines.add(it)
                propertyLinesList.add(propertyLines)
                propertyLines = mutableListOf()
            } else {
                propertyLines.add(it)
            }
        }
        return propertyLinesList

    }

    private fun getPropertyAnnotations(lines: List<String>): List<String> {
        return if (lines.size == 1) {
            val line = lines[0]
            val annotationPre = line.trim().split(" ")[0]
            if (annotationPre.contains("@")) {
                listOf(annotationPre)
            } else
                listOf("")
        } else if (lines.size > 1) {
            lines.subList(0, lines.size - 1).map { it.trim() }
        } else {
            listOf()
        }
    }

    private fun getPropertyKeyword(propertyLine: String): String {
        val subs = propertyLine.substringBefore(":").split(" ")
        return subs[subs.size - 2]
    }

    private fun getPropertyName(propertyLine: String): String {
        val subs = propertyLine.substringBefore(":").split(" ")
        return subs.last()
    }

    private fun getPropertyType(propertyLine: String, isLastLine: Boolean): String {
        if (propertyLine.contains("=")) {
            return propertyLine.substringAfter(":").substringBefore("//").split("=")[0].trim()
        } else {
            val substringBefore = propertyLine.substringAfter(":").substringBefore("//")
            return if (isLastLine)
                substringBefore.trim()
            else
                substringBefore.trim().dropLast(1)
        }
    }

    private fun getPropertyValue(propertyLine: String, isLastLine: Boolean): String {

        if (propertyLine.contains("=")) {
            val propertyValuePre = propertyLine.substringAfter(":").substringBefore("//").split("=")[1]
            return if (isLastLine) {
                propertyValuePre.trim()
            } else {
                propertyValuePre.trim().dropLast(1)
            }
        } else {
            return ""
        }

    }

    private fun getPropertyComment(propertyLine: String): String {
        return if (propertyLine.contains("//"))
            propertyLine.substringAfterLast("//").trim()
        else
            ""
    }
}