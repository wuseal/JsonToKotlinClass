package wu.seal.jsontokotlin

import wu.seal.jsontokotlin.interceptor.IInterceptor
import wu.seal.jsontokotlin.interceptor.InterceptorManager

class KotlinDataClassCodeMaker(private val rootClassName: String, private val json: String) {

    fun makeKotlinDataClassCode(): String {

        return if (needMakeKotlinCodeByKotlinDataClass()) {

            makeKotlinDataClassCode(InterceptorManager.getEnabledInterceptors())

        } else {
            KotlinCodeMaker(rootClassName, json).makeKotlinData()
        }
    }

    private fun needMakeKotlinCodeByKotlinDataClass(): Boolean {
        return InterceptorManager.getEnabledInterceptors().isNotEmpty()
    }

    fun makeKotlinDataClassCode(interceptors: List<IInterceptor>): String {
        val kotlinDataClasses = KotlinDataClassMaker(rootClassName = rootClassName, json = json).makeKotlinDataClasses()
        val interceptedDataClasses = kotlinDataClasses.map {it.applyInterceptors(interceptors)}
        val code = interceptedDataClasses.joinToString("\n\n") {
            it.getCode()
        }
        return code
    }
}