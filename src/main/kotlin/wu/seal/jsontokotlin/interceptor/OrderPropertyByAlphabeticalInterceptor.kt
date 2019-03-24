package wu.seal.jsontokotlin.interceptor

import wu.seal.jsontokotlin.classscodestruct.KotlinDataClass
import wu.seal.jsontokotlin.classscodestruct.Property


class OrderPropertyByAlphabeticalInterceptor : IKotlinDataClassInterceptor {

    override fun intercept(kotlinDataClass: KotlinDataClass): KotlinDataClass {


        val orderByAlphabeticalProperties = kotlinDataClass.properties.sortedBy { it.name }.resetIsLastFieldValueOfProperty()


        return kotlinDataClass.copy(properties = orderByAlphabeticalProperties)
    }


    private fun List<Property>.resetIsLastFieldValueOfProperty(): List<Property> {

        return mapIndexed { index, property ->

            property.copy(isLast = index == lastIndex)

        }

    }


}

