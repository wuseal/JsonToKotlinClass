package wu.seal.jsontokotlin

import wu.seal.jsontokotlin.interceptor.IKotlinDataClassInterceptor
import wu.seal.jsontokotlin.interceptor.InterceptorManager
import com.intellij.psi.PsiDirectory

class KotlinDataClassCodeMaker(private val rootClassName: String, private val json: String, private val directory: PsiDirectory) {

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

    fun makeKotlinDataClassCode(interceptors: List<IKotlinDataClassInterceptor>): String {

        // added directory variable to check duplicate name classes in the folder
        val kotlinDataClasses = KotlinDataClassMaker(rootClassName = rootClassName, json = json, directory = directory).makeKotlinDataClasses()


        val interceptedDataClasses = kotlinDataClasses.map {it.applyInterceptors(interceptors)}
        val code = interceptedDataClasses.joinToString("\n\n") {
            it.getCode()
        }
        return code
    }
}