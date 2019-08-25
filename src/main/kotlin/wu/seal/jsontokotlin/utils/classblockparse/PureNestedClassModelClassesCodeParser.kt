package wu.seal.jsontokotlin.utils.classblockparse

import wu.seal.jsontokotlin.classscodestruct.KotlinDataClass
import wu.seal.jsontokotlin.classscodestruct.Property
import wu.seal.jsontokotlin.utils.getClassesStringList

/**
 * parser which to parse the code generate in nested class model and no comment and no annotation, means pure.
 */
@Deprecated("Please use #NestedClassModelClassesCodeParser")
class PureNestedClassModelClassesCodeParser(private val classesCode: String) {

    fun parse(): KotlinDataClass {

        if (classesCode.contains("//") || classesCode.contains("@")) {
            throw IllegalArgumentException("Can't support this classes code for it has comment or annotations $classesCode")
        }

        return if (classesCode.contains("{").not()) {
            parsedToKotlinDataClass(classesCode)
        } else {
            val trimedClassesCode = classesCode.trim()
            val tobeParsedCode = trimedClassesCode.substringBefore("{")
            val tobeParsedNestedClassesCode = trimedClassesCode.substringAfter("{").substringBeforeLast("}")
            val parentClass = parsedToKotlinDataClass(tobeParsedCode)
            val subClasses = getClassesStringList(tobeParsedNestedClassesCode).map { parsedToKotlinDataClass(it) }
            parentClass.copy()
        }


    }

    private fun parsedToKotlinDataClass(classCode: String): KotlinDataClass {
        val tobeParsedCode = classCode.trim()
        val parsedKotlinDataClass = ClassCodeParser(tobeParsedCode).getKotlinDataClass()
        return toKotlinDataClass(parsedKotlinDataClass)
    }

    private fun toKotlinDataClass(parsedKotlinDataClass: ParsedKotlinDataClass): KotlinDataClass {
        val properties = parsedKotlinDataClass.properties.map {
            Property(
                    annotations = listOf(),
                    keyword = it.keyword,
                    originName = it.propertyName,
                    name = it.propertyName,
                    type = it.propertyType,
                    value = it.propertyValue,
                    comment = it.propertyComment,
                    isLast = it.isLastProperty,
                    originJsonValue = ""
            )
        }
        return KotlinDataClass(annotations = listOf(), name = parsedKotlinDataClass.name, properties = properties)
    }

}
