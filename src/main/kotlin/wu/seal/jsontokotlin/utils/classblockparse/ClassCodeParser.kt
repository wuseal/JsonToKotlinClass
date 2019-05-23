package wu.seal.jsontokotlin.utils.classblockparse

import wu.seal.jsontokotlin.utils.getClassNameFromClassBlockString

/**
 * parser for parse class block string, with this util, we could get the class struct elements
 */
class ClassCodeParser(private val classBlockString: String) {

    fun getKotlinDataClass(): ParsedKotlinDataClass {
        return ParsedKotlinDataClass(getClassAnnotations(), getClassName(), getProperties())
    }

    fun getClassName(): String {
        return getClassNameFromClassBlockString(classBlockString)
    }

    fun getClassAnnotations(): List<String> {
        val annotationsBlock = classBlockString.substringBefore("data class").trim()
        return annotationsBlock.split("\n").filter { it.contains("@") }.map { it.trim() }
    }

    fun getProperties(): List<ParsedKotlinDataClass.Property> {
        val propertiesBlock = classBlockString.substringAfter("(").substringBeforeLast(")").trim()

        val lines = propertiesBlock.split("\n")

        val properties = mutableListOf<ParsedKotlinDataClass.Property>()

        val propertyLinesList = getPropertyLinesList(lines)

        propertyLinesList.forEachIndexed { index, propertyBlockLines ->
            val annotations = getPropertyAnnotations(propertyBlockLines)
            val propertyKeyword = getPropertyKeyword(propertyBlockLines.last())
            val propertyName = getPropertyName(propertyBlockLines.last())
            val isLastLine = index == propertyLinesList.lastIndex
            val propertyType = getPropertyType(propertyBlockLines.last(), isLastLine)
            val propertyValue = getPropertyValue(propertyBlockLines.last(), isLastLine)
            val propertyComment = getPropertyComment(propertyBlockLines.last())
            properties.add(
                ParsedKotlinDataClass.Property(
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
            val deleteCommentLine = it.substringBefore("//")
            if ((deleteCommentLine.contains("val") || deleteCommentLine.contains("var")) && (deleteCommentLine.contains(
                    ":"
                ))
            ) {
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
            val removeCommentAndTypeProperty = line.substringBefore("//").substringBeforeLast(":").trim()
            val annotationPre = if (removeCommentAndTypeProperty.contains("(")) {
                removeCommentAndTypeProperty.substringBeforeLast(")") + ")"
            } else {
                removeCommentAndTypeProperty.trim().split(" ")[0]
            }
            if (annotationPre.startsWith("@")) {
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
        val stringBeforeLastColonWithoutComment = propertyLine.substringBefore("//").substringBeforeLast(":").trim()
        return when {
            stringBeforeLastColonWithoutComment.contains(")") -> {
                val noAnnotationString = stringBeforeLastColonWithoutComment.substringAfterLast(")").trim()
                val keyword = noAnnotationString.split(" ").first()
                keyword
            }
            stringBeforeLastColonWithoutComment.startsWith("@")-> {
                val keyword = stringBeforeLastColonWithoutComment.split(" ")[1]
                keyword
            }
            else -> {
                val keyword = stringBeforeLastColonWithoutComment.split(" ").first()
                keyword
            }
        }.trim()
    }

    private fun getPropertyName(propertyLine: String): String {

        val stringBeforeLastColonWithoutComment = propertyLine.substringBefore("//").substringBeforeLast(":").trim()
        return when {
            stringBeforeLastColonWithoutComment.contains(")") -> {
                val noAnnotationString = stringBeforeLastColonWithoutComment.substringAfterLast(")").trim()
                val splits = noAnnotationString.split(" ")
                val propertyName =
                    splits.filterIndexed { index, _ -> listOf(0).contains(index).not() }
                        .joinToString(" ")
                propertyName
            }
            stringBeforeLastColonWithoutComment.startsWith("@") -> {
                val splits = stringBeforeLastColonWithoutComment.split(" ")
                val propertyName =
                    splits.filterIndexed { index, _ -> listOf(0, 1).contains(index).not() }
                        .joinToString(" ")
                propertyName
            }
            else -> {
                val splits = stringBeforeLastColonWithoutComment.split(" ")
                val propertyName =
                    splits.filterIndexed { index, _ -> listOf(0).contains(index).not() }
                        .joinToString(" ")
                propertyName
            }
        }.trim()
    }

    private fun getPropertyType(propertyLine: String, isLastLine: Boolean): String {
        val deleteCommentPropertyLine = propertyLine.substringBefore("//")
        val typeAndValueBlock = deleteCommentPropertyLine.substringAfterLast(":")
        return if (typeAndValueBlock.contains("=")) {
            typeAndValueBlock.split("=")[0].trim()
        } else {
            if (isLastLine)
                typeAndValueBlock.trim()
            else
                typeAndValueBlock.trim().dropLast(1)
        }
    }

    private fun getPropertyValue(propertyLine: String, isLastLine: Boolean): String {
        val deleteCommentPropertyLine = propertyLine.substringBefore("//")
        val typeAndValueBlock = deleteCommentPropertyLine.substringAfterLast(":")
        return if (typeAndValueBlock.contains("=")) {
            val propertyValuePre = typeAndValueBlock.split("=")[1]
            if (isLastLine) {
                propertyValuePre.trim()
            } else {
                propertyValuePre.trim().dropLast(1)
            }
        } else {
            ""
        }

    }

    private fun getPropertyComment(propertyLine: String): String {
        return if (propertyLine.contains("//"))
            propertyLine.substringAfter("//").trim()
        else
            ""
    }
}
