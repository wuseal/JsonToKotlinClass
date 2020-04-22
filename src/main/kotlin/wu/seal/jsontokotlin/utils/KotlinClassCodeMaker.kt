package wu.seal.jsontokotlin.utils

import wu.seal.jsontokotlin.model.ConfigManager
import wu.seal.jsontokotlin.model.classscodestruct.KotlinClass
import wu.seal.jsontokotlin.interceptor.IKotlinClassInterceptor
import wu.seal.jsontokotlin.interceptor.InterceptorManager

class KotlinClassCodeMaker(private val kotlinClass: KotlinClass) {

    fun makeKotlinClassCode(): String {
        val interceptors = InterceptorManager.getEnabledKotlinDataClassInterceptors()
        return makeKotlinClassCode(interceptors)
    }

    private fun makeKotlinClassCode(interceptors: List<IKotlinClassInterceptor<KotlinClass>>): String {
        var kotlinClassForCodeGenerate = kotlinClass
        kotlinClassForCodeGenerate = kotlinClassForCodeGenerate.applyInterceptors(interceptors)
        return if (ConfigManager.isInnerClassModel) {
            kotlinClassForCodeGenerate.getCode()
        } else {
            val resolveNameConflicts = kotlinClassForCodeGenerate.resolveNameConflicts()
            val allModifiableClassesRecursivelyIncludeSelf = resolveNameConflicts
                    .getAllModifiableClassesRecursivelyIncludeSelf()
            allModifiableClassesRecursivelyIncludeSelf
                    .joinToString("\n\n") { it.getOnlyCurrentCode() }
        }
    }
}
