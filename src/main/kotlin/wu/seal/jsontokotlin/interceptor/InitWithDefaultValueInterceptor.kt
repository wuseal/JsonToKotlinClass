package wu.seal.jsontokotlin.interceptor

import wu.seal.jsontokotlin.ConfigManager
import wu.seal.jsontokotlin.DefaultValueStrategy
import wu.seal.jsontokotlin.classscodestruct.KotlinDataClass
import wu.seal.jsontokotlin.codeelements.getDefaultValue
import wu.seal.jsontokotlin.codeelements.getDefaultValueAllowNull

class InitWithDefaultValueInterceptor : IKotlinDataClassInterceptor {

    override fun intercept(kotlinDataClass: KotlinDataClass): KotlinDataClass {

        val getValue: (arg: String) -> String =
                if (ConfigManager.defaultValueStrategy == DefaultValueStrategy.AvoidNull) ::getDefaultValue
                else ::getDefaultValueAllowNull

        val initWithDefaultValueProperties = kotlinDataClass.properties.map {

            val initDefaultValue = getValue(it.type)

            it.copy(value = initDefaultValue)
        }

        return kotlinDataClass.copy(properties = initWithDefaultValueProperties)
    }

}
