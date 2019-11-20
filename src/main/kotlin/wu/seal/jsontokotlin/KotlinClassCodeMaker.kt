package wu.seal.jsontokotlin

import wu.seal.jsontokotlin.classscodestruct.KotlinClass
import wu.seal.jsontokotlin.interceptor.IKotlinDataClassInterceptor
import wu.seal.jsontokotlin.interceptor.InterceptorManager

class KotlinClassCodeMaker(private val kotlinClass: KotlinClass) {

    fun makeKotlinClassCode(): String {
        val interceptors = InterceptorManager.getEnabledKotlinDataClassInterceptors()
        return makeKotlinClassCode(interceptors)
    }

    private fun makeKotlinClassCode(interceptors: List<IKotlinDataClassInterceptor>): String {
        var kotlinClassForCodeGenerate = kotlinClass
        kotlinClassForCodeGenerate = kotlinClassForCodeGenerate.applyInterceptors(interceptors)
        return if (ConfigManager.isInnerClassModel) {
            kotlinClassForCodeGenerate.getCode()
        } else {
            kotlinClassForCodeGenerate.resolveNameConflicts()
                    .getAllUnModifiableClassesRecursively()
                    .joinToString("\n\n") { it.getOnlyCurrentCode() }
        }
    }
}
