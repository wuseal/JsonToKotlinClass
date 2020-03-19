package wu.seal.jsontokotlin.model.classscodestruct

data class Property(
        val annotations: List<Annotation> = listOf(),
        val keyword: String = "val",
        val originName: String,
        val originJsonValue: String? = "",
        val name: String = originName,
        val type: String,
        val value: String = "",
        val comment: String = "",
        val typeObject: KotlinClass,
        private var separatorBetweenAnnotationAndProperty: String = "\n"
) {
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
}
