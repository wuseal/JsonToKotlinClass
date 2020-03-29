package wu.seal.jsontokotlin.model.classscodestruct

import java.lang.UnsupportedOperationException

/**
 * Created by Seal.Wu on 2019-11-24
 * Kotlin class which could not be modified the code content without generic type
 */
abstract class UnModifiableNoGenericClass : NoGenericKotlinClass {
    override val modifiable: Boolean = false
    override val referencedClasses: List<KotlinClass> = listOf()
    override fun getCode() = throw UnsupportedOperationException("Dont support this function called on unModifiable Class")
    override fun getOnlyCurrentCode() = throw UnsupportedOperationException("Dont support this function called on unModifiable Class")
    override fun rename(newName: String) = throw UnsupportedOperationException("Dont support this function called on unModifiable Class")
    override fun replaceReferencedClasses(replaceRule: Map<KotlinClass, KotlinClass>): KotlinClass = throw UnsupportedOperationException("Dont support this function called on unModifiable Class")
}