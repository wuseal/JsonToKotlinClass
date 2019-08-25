package wu.seal.jsontokotlin

import wu.seal.jsontokotlin.classscodestruct.KotlinDataClass
import wu.seal.jsontokotlin.classscodestruct.Property
import wu.seal.jsontokotlin.interceptor.IKotlinDataClassInterceptor
import wu.seal.jsontokotlin.interceptor.InterceptorManager
import wu.seal.jsontokotlin.utils.IgnoreCaseStringSet

class KotlinDataClassCodeMaker(private val kotlinDataClass: KotlinDataClass) {

    fun makeKotlinDataClassCode(): String {
        val interceptors = InterceptorManager.getEnabledKotlinDataClassInterceptors()
        return makeKotlinDataClassCode(interceptors)
    }

    private fun makeKotlinDataClassCode(interceptors: List<IKotlinDataClassInterceptor>): String {
        var kotlinDataClass = kotlinDataClass
        kotlinDataClass = kotlinDataClass.applyInterceptors(interceptors)
        return if (ConfigManager.isInnerClassModel) {
            kotlinDataClass.getCode()
        } else {
            kotlinDataClass.resolveInnerConflictClassName().getSplitClassCode()
        }
    }
}

private fun KotlinDataClass.getSplitClassCode(): String {

    val dataClasses: List<KotlinDataClass> = getSplitClasses()

    return dataClasses.joinToString("\n\n") { it.getCurrentClassCode() }
}

fun KotlinDataClass.getSplitClasses(): List<KotlinDataClass> {
    val splitClasses = mutableListOf<KotlinDataClass>()
    splitClasses.apply {
        add(this@getSplitClasses)
        properties.forEach {
            it.typeObject?.let { typeObject ->
                addAll(typeObject.getSplitClasses())
            }
        }
    }
    return splitClasses
}

/**
 * Keep all class name inside this Kotlin Data Class unique against the [existClassNames]
 */
fun KotlinDataClass.resolveInnerConflictClassName(existClassNames: IgnoreCaseStringSet = IgnoreCaseStringSet()): KotlinDataClass {

    var thisNoneConflictName = name
    if (existClassNames.contains(thisNoneConflictName)) {
        thisNoneConflictName = getNoneConflictClassName(existClassNames, name)
    }
    existClassNames.add(thisNoneConflictName)

    val newProperties = mutableListOf<Property>()

    properties.forEach { property ->
        val refKotlinDataClassName = property.typeObject?.name
        if (refKotlinDataClassName != null) {
            //try to resolve it's type object's conflict class names add it to the new property list
            val newRefKotlinDataclass = property.typeObject.resolveInnerConflictClassName(existClassNames)
            val newProperty = property.copy(type = newRefKotlinDataclass.name, typeObject = newRefKotlinDataclass)
            newProperties.add(newProperty)
        } else {
            //Not a Kotlin data class ref type property, add it to new properties list
            newProperties.add(property)
        }
    }
    return copy(name = thisNoneConflictName, properties = newProperties)
}

private fun getNoneConflictClassName(existClassNames: Set<String>, conflictClassName: String): String {
    var newNoneConflictClassName = conflictClassName
    while (existClassNames.contains(newNoneConflictClassName)) {
        newNoneConflictClassName += "X"
    }
    return newNoneConflictClassName
}
