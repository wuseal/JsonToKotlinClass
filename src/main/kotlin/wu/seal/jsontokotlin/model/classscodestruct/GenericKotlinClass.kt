package wu.seal.jsontokotlin.model.classscodestruct

/**
 * Created by Seal.Wu On 2019-11-24
 * Indicate the class contains generic type
 */
interface GenericKotlinClass : KotlinClass {

    override val hasGeneric: Boolean
        get() = true

    /**
     * generic class must not have any referenced classes, all referenced classes should be hold by the class who declare
     * this generic class
     */
    override val referencedClasses: List<KotlinClass>
        get() = listOf()
}

interface NoGenericKotlinClass : KotlinClass {

    override val generic: KotlinClass
        get() = KotlinClass.ANY

    override val hasGeneric: Boolean
        get() = false

}

fun KotlinClass.getAllGenericsRecursively(): List<KotlinClass> {
    val allGenerics = mutableListOf<KotlinClass>()
    if (hasGeneric) {
        return allGenerics.apply {
            add(generic)
            var nextGeneric = generic
            while (nextGeneric.hasGeneric) {
                add(nextGeneric.generic)
                nextGeneric = nextGeneric.generic
            }
        }
    }
    return allGenerics
}