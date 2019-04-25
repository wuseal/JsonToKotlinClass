package wu.seal.jsontokotlin.interceptor

import wu.seal.jsontokotlin.classscodestruct.KotlinDataClass
import wu.seal.jsontokotlin.codeelements.getDefaultValue

class InitWithDefaultValueInterceptor : IKotlinDataClassInterceptor {

    override fun intercept(kotlinDataClass: KotlinDataClass): KotlinDataClass {


        val initWithDefaultValueProperties = kotlinDataClass.properties.map {

            val initDefaultValue = getDefaultValue(it.type)

            it.copy(value = initDefaultValue)
        }


        return kotlinDataClass.copy(properties = initWithDefaultValueProperties)
    }

}
