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
            removeDuplicateClassCode: String): List<ParsedKotlinDataClass> {

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


    /**
     * generates Kotlin data classes without having any class name conflicts
     */
    fun generateKotlinDataClassesWithNonConflictNames(removeDuplicateClassCode: String): List<ParsedKotlinDataClass> {
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


    /**
     * gets the list of non conflicting class names by appending extra character
     */
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


    /**
     * updates the class names with renamed class names
     */
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
            it.properties.forEach { p ->
                if (p.kotlinDataClassPropertyTypeRef == originDataClass) {
                    p.kotlinDataClassPropertyTypeRef = newKotlinDataClass
                }
            }
        }
    }

    /**
     * Returns list of Classes with synchronized property based on renamed classes
     */
    fun synchronizedPropertyTypeWithTypeRef(unSynchronizedTypeClasses: List<ParsedKotlinDataClass>): List<ParsedKotlinDataClass> {
        return unSynchronizedTypeClasses.map { dataClass: ParsedKotlinDataClass ->

            val newProperties = dataClass.properties.map { property ->
                if (property.kotlinDataClassPropertyTypeRef != ParsedKotlinDataClass.NONE) {
                    val rawPropertyReferenceType = getRawType(getChildType(property.propertyType))
                    val tobeReplaceNewType =
                            property.propertyType.replace(rawPropertyReferenceType, property.kotlinDataClassPropertyTypeRef.name)
                    if (property.propertyValue.isNotBlank()) {
                        property.copy(propertyType = tobeReplaceNewType, propertyValue = getDefaultValue(tobeReplaceNewType))
                    } else
                        property.copy(propertyType = tobeReplaceNewType)
                } else {
                    property
                }
            }
            dataClass.copy(properties = newProperties)
        }
    }

    /**
     * builds the reference for each property in the data classes
     */
    fun buildTypeReference(classes: List<ParsedKotlinDataClass>): List<ParsedKotlinDataClass> {

        val notBeenReferencedClass = mutableListOf<ParsedKotlinDataClass>().apply {
            addAll(classes)
            removeAt(0)
        }

        val classNameList = notBeenReferencedClass.map { it.name }.toMutableList()

        classes.forEach {
            buildClassTypeReference(it, classNameList, notBeenReferencedClass)
        }

        return classes
    }

    private fun buildClassTypeReference(tobeBuildTypeReferenceClass: ParsedKotlinDataClass, classNameList: MutableList<String>, notBeenReferencedClass: MutableList<ParsedKotlinDataClass>) {
        tobeBuildTypeReferenceClass.properties.forEach { property ->
            val indexOfClassName =
                    classNameList.indexOf(getRawType(getChildType(property.propertyType)))
            if (indexOfClassName != -1) {
                val referencedClass = notBeenReferencedClass[indexOfClassName]
                notBeenReferencedClass.remove(referencedClass)
                classNameList.removeAt(indexOfClassName)
                notBeenReferencedClass.remove(referencedClass)
                property.kotlinDataClassPropertyTypeRef = referencedClass

                buildClassTypeReference(referencedClass,classNameList,notBeenReferencedClass)

            }
        }
    }

    private fun changeClassNameIfCurrentListContains(classesNames: List<String>, className: String): String {
        var newClassName = className
        while (classesNames.contains(newClassName)) {
            newClassName += "X"
        }
        return newClassName
    }

}
