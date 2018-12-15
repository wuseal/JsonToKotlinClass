package wu.seal.jsontokotlin


import wu.seal.jsontokotlin.codeelements.getDefaultValue
import wu.seal.jsontokotlin.classscodestruct.KotlinDataClass
import wu.seal.jsontokotlin.utils.*
import wu.seal.jsontokotlin.utils.classblockparse.ClassCodeParser
import wu.seal.jsontokotlin.utils.classblockparse.NestedClassModelClassesCodeParser
import wu.seal.jsontokotlin.utils.classblockparse.ParsedKotlinDataClass

class KotlinDataClassMaker(private val rootClassName: String, private val json: String) {



    private val renamedClassNames = mutableListOf<Pair<String, String>>()

    fun makeKotlinDataClasses(): List<KotlinDataClass> {

        val codeMaker = KotlinCodeMaker(rootClassName, json)

        //code string after removing duplicate class structure code
        val code = ClassCodeFilter.removeDuplicateClassCode(codeMaker.makeKotlinData())

        return if (ConfigManager.isInnerClassModel) {
            listOf(NestedClassModelClassesCodeParser(code).parse())
        } else {

            // create the list of non duplicate classes
            val parsedKotlinClasses: List<ParsedKotlinDataClass> =
                    makeKotlinDataClasses(code)

            return parsedKotlinClasses.map { KotlinDataClass.fromParsedKotlinDataClass(it) }
        }
    }

    // method to make in class data classes for JsonToKotlinClass, renames same class name to different
    private fun makeKotlinDataClasses(
            removeDuplicateClassCode: String) : List<ParsedKotlinDataClass>{

        val kotlinClasses = generateKotlinDataClassesWithNonConflictNames(removeDuplicateClassCode = removeDuplicateClassCode)

        val notifyMessage = buildString {
            append("${kotlinClasses.size} Kotlin Data Classes generated successful")
            if (renamedClassNames.isNotEmpty()) {
                append("\n")
                append("These class names has been auto renamed to new names:\n ${renamedClassNames.map { it.first + " -> " + it.second }.toList()}")
            }
        }
        showNotify(notifyMessage, null)


        return kotlinClasses
    }

    fun generateKotlinDataClassesWithNonConflictNames(removeDuplicateClassCode: String) : List<ParsedKotlinDataClass>
    {
        val classes =
                getClassesStringList(removeDuplicateClassCode).map { ClassCodeParser(it).getKotlinDataClass() }

        /**
         * Build Property Type reference to ParsedKotlinDataClass
         * Only pre class property type could reference behind classes
         */
        val buildRefClasses = buildTypeReference(classes)

        val newClassNames = getNoneConflictClassNames(buildRefClasses)

        val newKotlinClasses = updateClassNames(buildRefClasses, newClassNames)


        return synchronizedPropertyTypeWithTypeRef(newKotlinClasses)

    }



    fun getNoneConflictClassNames(
            buildRefClasses: List<ParsedKotlinDataClass>): List<String> {

        val resolveSameConflictClassesNames = mutableListOf<String>()
        buildRefClasses.forEach {
            val originClassName = it.name
            val newClassName = changeClassNameIfCurrentListContains(resolveSameConflictClassesNames, originClassName)
            resolveSameConflictClassesNames.add(newClassName)
        }

        return resolveSameConflictClassesNames
    }



    fun updateClassNames(
            dataClasses: List<ParsedKotlinDataClass>,
            newClassNames: List<String>
    ): List<ParsedKotlinDataClass> {

        val newKotlinClasses = dataClasses.toMutableList()

        newKotlinClasses.forEachIndexed { index, kotlinDataClass ->

            val newClassName = newClassNames[index]
            val originClassName = kotlinDataClass.name

            if (newClassName != originClassName) {
                renamedClassNames.add(Pair(originClassName, newClassName))
                val newKotlinDataClass = kotlinDataClass.copy(name = newClassName)
                newKotlinClasses[index] = newKotlinDataClass
                updateTypeRef(dataClasses, kotlinDataClass, newKotlinDataClass)
            }
        }

        return newKotlinClasses
    }

    private fun updateTypeRef(
            classes: List<ParsedKotlinDataClass>,
            originDataClass: ParsedKotlinDataClass,
            newKotlinDataClass: ParsedKotlinDataClass
    ) {
        classes.forEach {
            it.properties.forEach {
                if (it.kotlinDataClassPropertyTypeRef == originDataClass) {
                    it.kotlinDataClassPropertyTypeRef = newKotlinDataClass
                }
            }
        }
    }

    fun synchronizedPropertyTypeWithTypeRef(unSynchronizedTypeClasses: List<ParsedKotlinDataClass>): List<ParsedKotlinDataClass> {
        return unSynchronizedTypeClasses.map { dataClass: ParsedKotlinDataClass ->

            val newProperties = dataClass.properties.map { it ->
                if (it.kotlinDataClassPropertyTypeRef != ParsedKotlinDataClass.NONE) {
                    val rawPropertyReferenceType = getRawType(getChildType(it.propertyType))
                    val tobeReplaceNewType =
                            it.propertyType.replace(rawPropertyReferenceType, it.kotlinDataClassPropertyTypeRef.name)
                    if (it.propertyValue.isNotBlank()) {
                        it.copy(propertyType = tobeReplaceNewType, propertyValue = getDefaultValue(tobeReplaceNewType))
                    } else
                        it.copy(propertyType = tobeReplaceNewType)
                } else {
                    it
                }
            }
            dataClass.copy(properties = newProperties)
        }
    }

    fun buildTypeReference(classes: List<ParsedKotlinDataClass>): List<ParsedKotlinDataClass> {
        val classNameList = classes.map { it.name }

        /**
         * Build Property Type reference to ParsedKotlinDataClass
         * Only pre class property type could reference behind classes
         */
        classes.forEachIndexed { index, kotlinDataClass ->
            kotlinDataClass.properties.forEachIndexed { _, property ->
                val indexOfClassName =
                        classNameList.firstIndexAfterSpecificIndex(getRawType(getChildType(property.propertyType)), index)
                if (indexOfClassName != -1) {
                    property.kotlinDataClassPropertyTypeRef = classes[indexOfClassName]
                }
            }
        }

        return classes
    }

    private fun changeClassNameIfCurrentListContains(classesNames: List<String>, className: String): String {
        var newClassName = className
        while (classesNames.contains(newClassName)) {
            newClassName += "X"
        }
        return newClassName
    }

}