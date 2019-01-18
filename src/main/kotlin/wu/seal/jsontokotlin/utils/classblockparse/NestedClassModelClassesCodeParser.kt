package wu.seal.jsontokotlin.utils.classblockparse

import wu.seal.jsontokotlin.classscodestruct.KotlinDataClass

class NestedClassModelClassesCodeParser(private val nestedClassCode: String) {

    fun parse(): KotlinDataClass {

        if (nestedClassCode.contains(
                "\n) {\n"
            ).not()
        ) {
            return KotlinDataClass.fromParsedKotlinDataClass(ClassCodeParser(nestedClassCode).getKotlinDataClass())
        }
        val parenClassCode = nestedClassCode.substringBefore("\n) {\n") + "\n)"
        val parenParsedKotlinDataClass = ClassCodeParser(parenClassCode).getKotlinDataClass()
        val parenKotlinDataClass = KotlinDataClass.fromParsedKotlinDataClass(parenParsedKotlinDataClass)

        val nestedSubClassesCode = nestedClassCode.substringAfter("\n) {\n").substringBeforeLast("}").trim()
        val nestedClasses = splitSubClasses(nestedSubClassesCode).map { NestedClassModelClassesCodeParser(it).parse() }
        return parenKotlinDataClass.copy(nestedClasses = nestedClasses)
    }


    private fun splitSubClasses(classesString: String): List<String> {

        val classStrings = mutableListOf<String>()
        val lines = classesString.split("\n").map { it.trim() }
        var classStringBuilder = StringBuilder()
        var dataclassStringCount = 0
        var backParenthesesCount = 0
        var backBigParenthesesCount = 0
        lines.forEach {
            if (it.matches("^(data )?class.+".toRegex())) {
                classStringBuilder.append("\n")
                classStringBuilder.append(it)
                dataclassStringCount++
            } else if (it == ")") {
                classStringBuilder.append("\n")
                classStringBuilder.append(it)
                backParenthesesCount++
                if (backBigParenthesesCount + backParenthesesCount == dataclassStringCount) {
                    classStrings.add(classStringBuilder.toString())
                    classStringBuilder = StringBuilder()
                }
            } else if (it == "}") {
                classStringBuilder.append("\n")
                classStringBuilder.append(it)
                backBigParenthesesCount++
                if (backBigParenthesesCount + backParenthesesCount == dataclassStringCount) {
                    classStrings.add(classStringBuilder.toString())
                    classStringBuilder = StringBuilder()
                }
            } else {
                classStringBuilder.append("\n")
                classStringBuilder.append(it)
            }
        }
        return classStrings
    }
}
