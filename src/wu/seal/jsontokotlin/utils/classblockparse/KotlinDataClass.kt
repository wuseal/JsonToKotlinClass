package wu.seal.jsontokotlin.utils.classblockparse

import wu.seal.jsontokotlin.utils.getIndent

data class KotlinDataClass(val annotations: List<String>, val name: String, val properties: List<Property>) {

    companion object {
        /**
         * Represent this is Not a KotlinDataClass Type
         */
        val NONE = KotlinDataClass(listOf(), "", listOf())
    }

    override fun toString(): String {
        return buildString {

            annotations.forEach {
                if (it.isNotBlank()) {
                    append(it)
                    append("\n")
                }
            }
            append("data class ").append(name).append("(")
            append("\n")
            properties.forEach {
                append(it.toString())
                append("\n")
            }
            append(")")
        }
    }

    data class Property(
        val annotations: List<String>,
        val keyword: String,
        val propertyName: String,
        val propertyType: String,
        val propertyValue: String,
        val propertyComment: String,
        val isLastProperty: Boolean,
        var kotlinDataClassPropertyTypeRef: KotlinDataClass = NONE
    ) {
        override fun toString(): String {
            val indent = getIndent()
            return buildString {
                if (annotations.size > 1) {

                    annotations.forEach {
                        if (it.isNotBlank()) {
                            append(indent).append(it).append("\n")
                        }
                    }

                    append(indent).append(keyword).append(" ").append(propertyName)
                        .append(":").append(" ").append(propertyType)

                    if (propertyValue.isNotBlank()) {
                        append(" = ").append(propertyValue)
                    }
                    if (isLastProperty.not()) {
                        append(",")
                    }
                    if (propertyComment.isNotBlank()) {
                        append(" // ").append(propertyComment)
                    }
                } else {
                    append(indent)
                    if (annotations.size == 1 && annotations[0].isNotBlank()) {
                        append(annotations[0])
                        append(" ")
                    }
                    append(keyword).append(" ").append(propertyName)
                        .append(":").append(" ").append(propertyType)

                    if (propertyValue.isNotBlank()) {
                        append(" = ").append(propertyValue)
                    }
                    if (isLastProperty.not()) {
                        append(",")
                    }
                    if (propertyComment.isNotBlank()) {
                        append(" // ").append(propertyComment)
                    }
                }

            }
        }

    }
}