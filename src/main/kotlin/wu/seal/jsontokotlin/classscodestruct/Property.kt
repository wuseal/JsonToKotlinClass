package wu.seal.jsontokotlin.classscodestruct

import wu.seal.jsontokotlin.utils.classblockparse.ParsedKotlinDataClass

data class Property(
        val annotations: List<Annotation>,
        val keyword: String,
        val name: String,
        val type: String,
        val value: String,
        val comment: String,
        val isLast: Boolean,
        val refTypeId: Int = -1, // the id of property type,if can't reference in current generate classes ,use the default value -1
        val originName: String,
        val originJsonValue: String? = ""
) {

    private var separatorBetweenAnnotationAndProperty = "\n"

    fun letLastAnnotationStayInSameLine() {
        separatorBetweenAnnotationAndProperty = " "
    }

    fun getCode(): String {

        return buildString {
            if (annotations.isNotEmpty()) {
                val annotationsCode = annotations.joinToString("\n") { it.getAnnotationString() }
                if (annotationsCode.isNotBlank()) {
                    append(annotationsCode).append(separatorBetweenAnnotationAndProperty)
                }
            }
            append(keyword).append(" ").append(name).append(": ").append(type)
            if (value.isNotBlank()) {
                append(" = ").append(value)
            }
        }
    }

    fun toParsedProperty(): ParsedKotlinDataClass.Property {

        val propertyAnnotationCodeList = annotations.map { annotation -> annotation.getAnnotationString() }

        return ParsedKotlinDataClass.Property(
                propertyAnnotationCodeList,
                keyword,
                name,
                type,
                value,
                comment,
                isLast
        )

    }

    fun getRawName(): String {

        return if (annotations.isNotEmpty()) {
            val notBlankRawNames = annotations.map { it.rawName }.filter { it.isNotBlank() }
            if (notBlankRawNames.isNotEmpty()) {
                notBlankRawNames[0]
            } else {
                ""
            }
        } else {
            ""
        }
    }

    companion object {

        fun fromParsedProperty(parsedProperty: ParsedKotlinDataClass.Property): Property {
            val annotations = parsedProperty.annotations.map { Annotation.fromAnnotationString(it) }
            return Property(
                    annotations = annotations,
                    keyword = parsedProperty.keyword,
                    name = parsedProperty.propertyName,
                    type = parsedProperty.propertyType,
                    value = parsedProperty.propertyValue,
                    comment = parsedProperty.propertyComment,
                    isLast = parsedProperty.isLastProperty,
                    refTypeId = -1,
                    originName = parsedProperty.propertyName,
                    originJsonValue = if (parsedProperty.propertyComment.trim().equals("null")) null else parsedProperty.propertyComment.trim()
            )
        }
    }
}
