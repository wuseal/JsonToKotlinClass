package wu.seal.jsontokotlin

import wu.seal.jsontokotlin.interceptor.IKotlinDataClassInterceptor
import wu.seal.jsontokotlin.interceptor.InterceptorManager

class KotlinDataClassCodeMaker( private val rootClassName: String, private val json: String ) {

    fun makeKotlinDataClassCode(): String {

        return if (needMakeKotlinCodeByKotlinDataClass()) {

            makeKotlinDataClassCode(InterceptorManager.getEnabledKotlinDataClassInterceptors())

        } else {
            KotlinCodeMaker(rootClassName, json).makeKotlinData()
        }
    }

    private fun needMakeKotlinCodeByKotlinDataClass(): Boolean {
        return InterceptorManager.getEnabledKotlinDataClassInterceptors().isNotEmpty()
    }

    private fun makeKotlinDataClassCode(interceptors: List<IKotlinDataClassInterceptor>): String {

        val kotlinDataClasses = KotlinDataClassMaker(rootClassName = rootClassName, json = json).makeKotlinDataClasses()

        val interceptedDataClasses = kotlinDataClasses.map {it.applyInterceptors(interceptors)}
        return interceptedDataClasses.joinToString("\n\n") {
            it.getCode()
        }
    }
}
