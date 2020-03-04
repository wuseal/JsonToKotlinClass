package wu.seal.jsontokotlin.interceptor

import wu.seal.jsontokotlin.model.ConfigManager
import wu.seal.jsontokotlin.model.DefaultValueStrategy
import wu.seal.jsontokotlin.model.classscodestruct.KotlinDataClass
import wu.seal.jsontokotlin.model.codeelements.getDefaultValue
import wu.seal.jsontokotlin.model.codeelements.getDefaultValueAllowNull

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
