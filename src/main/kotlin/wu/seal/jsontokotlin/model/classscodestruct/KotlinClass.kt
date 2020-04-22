package wu.seal.jsontokotlin.model.classscodestruct

import wu.seal.jsontokotlin.interceptor.IKotlinClassInterceptor
import wu.seal.jsontokotlin.utils.IgnoreCaseStringSet
import wu.seal.jsontokotlin.utils.LogUtil

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
     * Indicate if this class contains generic type
     */
    val hasGeneric: Boolean


    val generic: KotlinClass

    /**
     * get the code (include referenced classes) string for writing into file or printing out
     */
    fun getCode(): String

    /**
     * only the current class code not include the referenced class for writing into file or printing out
     */
    fun getOnlyCurrentCode(): String

    fun <T : KotlinClass> applyInterceptors(enabledKotlinClassInterceptors: List<IKotlinClassInterceptor<T>>): KotlinClass = this

    fun <T : KotlinClass> applyInterceptor(classInterceptor: IKotlinClassInterceptor<T>): KotlinClass = applyInterceptors(listOf(classInterceptor))

    fun rename(newName: String): KotlinClass


    fun replaceReferencedClasses(replaceRule: Map<KotlinClass, KotlinClass>): KotlinClass

    /**
     * Keep all class name inside this Kotlin Data Class unique against the [existClassNames]
     */
    fun resolveNameConflicts(existClassNames: IgnoreCaseStringSet = IgnoreCaseStringSet()): KotlinClass {
        var thisNoneConflictName = name
        if (existClassNames.contains(thisNoneConflictName)) {
            thisNoneConflictName = getNoneConflictClassName(existClassNames, name)
        }
        existClassNames.add(thisNoneConflictName)
        val classReplaceRule = referencedClasses.filter { it.modifiable }.associateWith { it.resolveNameConflicts(existClassNames) }
        return rename(thisNoneConflictName).replaceReferencedClasses(classReplaceRule)
    }

    /**
     * Obtain all the kotlin class reference by this class and referenced class by referenced class Recursively
     */
    private fun getAllRefClassesRecursively(): List<KotlinClass> {
        val allRefClasses = mutableListOf<KotlinClass>()
        if (referencedClasses.isEmpty()) {
            return allRefClasses
        }
        allRefClasses.addAll(referencedClasses)
        LogUtil.i("getAllRefClassesRecursively added referenced class ${referencedClasses.map { it.name }}")
        allRefClasses.addAll(referencedClasses.flatMap { it.getAllRefClassesRecursively() })
        return allRefClasses
    }

    /**
     * Obtain all the unModifiable kotlin class reference by this class and referenced class by referenced class Recursively
     */
    fun getAllModifiableRefClassesRecursively(): List<KotlinClass> {

        return getAllRefClassesRecursively().filter { it.modifiable }
    }

    /**
     * Obtain all the modifiable kotlin class reference by this class and referenced class by referenced class Recursively
     * also include self class
     */
    fun getAllModifiableClassesRecursivelyIncludeSelf(): List<KotlinClass> {

        return getAllModifiableRefClassesRecursively().toMutableList().apply {
            if (modifiable) {
                add(0, this@KotlinClass)
            }
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
        val ANY = object : UnModifiableNoGenericClass() {
            override val name: String = "Any"
        }
        val STRING = object : UnModifiableNoGenericClass() {
            override val name: String = "String"
        }
        val BOOLEAN = object : UnModifiableNoGenericClass() {
            override val name: String = "Boolean"
        }
        val INT = object : UnModifiableNoGenericClass() {
            override val name: String = "Int"
        }
        val DOUBLE = object : UnModifiableNoGenericClass() {
            override val name: String = "Double"
        }
        val FLOAT = object : UnModifiableNoGenericClass() {
            override val name: String = "Float"
        }
        val LONG = object : UnModifiableNoGenericClass() {
            override val name: String = "Long"
        }

    }
}
