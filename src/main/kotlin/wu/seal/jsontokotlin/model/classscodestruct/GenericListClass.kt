package wu.seal.jsontokotlin.model.classscodestruct

import wu.seal.jsontokotlin.interceptor.IKotlinClassInterceptor
import wu.seal.jsontokotlin.utils.LogUtil

/**
 * Created by Seal.Wu on 2019-11-23
 *  present a list class type like List<Any>
 */
data class GenericListClass(override val generic: KotlinClass) : UnModifiableGenericClass() {
    override val name: String
        get() = "List<${generic.name}>"

    override val hasGeneric: Boolean = true

    override fun replaceReferencedClasses(replaceRule: Map<KotlinClass, KotlinClass>): GenericListClass {
        return if (generic is GenericListClass) {
            copy(generic = generic.replaceReferencedClasses(replaceRule))
        } else {
            val replacement = replaceRule[generic]
            if (replacement == null) {
                LogUtil.i("Can't find replacement for ${generic.name}")
                this
            } else {
                copy(generic = replacement)
            }
        }
    }

    override fun <T : KotlinClass> applyInterceptors(enabledKotlinClassInterceptors: List<IKotlinClassInterceptor<T>>): KotlinClass {
        return copy(generic = generic.applyInterceptors(enabledKotlinClassInterceptors))
    }

}