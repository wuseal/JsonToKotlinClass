package wu.seal.jsontokotlin.classscodestruct

import wu.seal.jsontokotlin.interceptor.IKotlinDataClassInterceptor
import wu.seal.jsontokotlin.utils.IgnoreCaseStringSet

/**
 * Kotlin Class Interface
 * Created by Seal.Wu on 2019/11/17.
 */
interface KotlinClass {

    /*
     * the name of this class
     */
    val name: String

    /**
     *  the imported classes used in this class
     */
    val referencedClasses: List<KotlinClass>

    /**
     * Indicate if this class code could be modified
     */
    val modifiable: Boolean

    /**
     * get the code (include referenced classes) string for writing into file or printing out
     */
    fun getCode(): String

    /**
     * only the current class code not include the referenced class for writing into file or printing out
     */
    fun getOnlyCurrentCode(): String

    fun applyInterceptors(enabledKotlinDataClassInterceptors: List<IKotlinDataClassInterceptor>): KotlinClass = this

    fun applyInterceptor(dataClassInterceptor: IKotlinDataClassInterceptor): KotlinClass = applyInterceptors(listOf(dataClassInterceptor))

    fun rename(newName: String): KotlinClass

    fun replaceReferencedClasses(referencedClasses: List<KotlinClass>): KotlinClass

    /**
     * Keep all class name inside this Kotlin Data Class unique against the [existClassNames]
     */
    fun resolveNameConflicts(existClassNames: IgnoreCaseStringSet = IgnoreCaseStringSet()): KotlinClass {
        var thisNoneConflictName = name
        if (existClassNames.contains(thisNoneConflictName)) {
            thisNoneConflictName = getNoneConflictClassName(existClassNames, name)
        }
        existClassNames.add(thisNoneConflictName)
        val newReferencedClasses = referencedClasses.map { it.resolveNameConflicts(existClassNames) }
        return rename(thisNoneConflictName).replaceReferencedClasses(newReferencedClasses)
    }

    /**
     * Obtain all the kotlin class reference by this class and referenced class by referenced class Recursively
     */
    private fun getAllRefClassesRecursively(): List<KotlinClass> {
        val allRefClasses = mutableListOf<KotlinClass>()
        allRefClasses.addAll(referencedClasses)
        allRefClasses.addAll(referencedClasses.flatMap { it.getAllRefClassesRecursively() })
        return allRefClasses
    }

    /**
     * Obtain all the unModifiable kotlin class reference by this class and referenced class by referenced class Recursively
     */
    fun getAllUnModifiableRefClassesRecursively(): List<KotlinClass> {

        return getAllRefClassesRecursively().filter { it.modifiable }
    }

    /**
     * Obtain all the unModifiable kotlin class reference by this class and referenced class by referenced class Recursively
     * also include self class
     */
    fun getAllUnModifiableClassesRecursively(): List<KotlinClass> {

        return getAllUnModifiableRefClassesRecursively().toMutableList().apply {
            add(0, this@KotlinClass)
        }
    }

    private fun getNoneConflictClassName(existClassNames: Set<String>, conflictClassName: String): String {
        var newNoneConflictClassName = conflictClassName
        while (existClassNames.contains(newNoneConflictClassName)) {
            newNoneConflictClassName += "X"
        }
        return newNoneConflictClassName
    }

    companion object {
        val ANY = object : UnModifiableClass() {
            override val name: String = "Any"
        }
        val STRINNG = object : UnModifiableClass() {
            override val name: String = "String"
        }
        val BOOLEAN = object : UnModifiableClass() {
            override val name: String = "Boolean"
        }
        val INT = object : UnModifiableClass() {
            override val name: String = "Int"
        }
        val DOUBLE = object : UnModifiableClass() {
            override val name: String = "Double"
        }
        val FLOAT = object : UnModifiableClass() {
            override val name: String = "Float"
        }
        val LONG = object : UnModifiableClass() {
            override val name: String = "Long"
        }

    }
}
