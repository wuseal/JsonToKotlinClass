package extensions.nstd

import com.winterbe.expekt.should
import org.junit.Before
import org.junit.Test
import wu.seal.jsontokotlin.generateKotlinClass
import wu.seal.jsontokotlin.interceptor.InterceptorManager
import wu.seal.jsontokotlin.model.builder.KotlinEnumCodeBuilder
import wu.seal.jsontokotlin.model.classscodestruct.EnumClass
import wu.seal.jsontokotlin.model.classscodestruct.KotlinClass
import wu.seal.jsontokotlin.test.TestConfig

/**
 * Created by Nstd on 2020/7/1 14:36.
 */
class KotlinEnumCodeBuilderTest : ICodeBuilderTest<EnumClass> {
    val json = """{
  "${"$"}schema": "http://json-schema.org/draft-04/schema#",
  "title": "LogEntry",
  "type": "object",
  "additionalProperties": false,
  "properties": {
    "id": {
      "type": [
        "null",
        "string"
      ]
    },
    "timestamp": {
      "type": "string",
      "format": "date-time"
    },
    "removed": {
      "type": "boolean"
    },
    "typeA": {
      "description": "Тип события",
      "oneOf": [
        {
          "${"$"}ref": "#/definitions/LogEventType"
        }
      ]
    },
    "typeB": {
      "type": "integer",
      "description": "Подсобытие (набор значений зависит от действия)",
      "format": "int32"
    },
    "ip": {
      "type": [
        "null",
        "string"
      ]
    },
    "userId": {
      "type": [
        "null",
        "string"
      ]
    },
    "deviceId": {
      "type": [
        "null",
        "string"
      ],
      "description": "ID устройства из коллекции Devices"
    },
    "phone": {
      "type": [
        "null",
        "string"
      ]
    },
    "hwid": {
      "type": [
        "null",
        "string"
      ]
    },
    "osUserId": {
      "type": [
        "null",
        "string"
      ]
    }
  },
  "definitions": {
    "LogEventType": {
      "type": "integer",
      "description": "",
      "x-enumNames": [
        "Undefined",
        "Auth"
      ],
      "enum": [
        0,
        1
      ]
    }
  }
}
    """.trimIndent()

    val expectedEnum = """
        enum class LogEventType(val value: Int) {
            Undefined(0),

            Auth(1);
        }
    """.trimIndent()

    @Before
    override fun setUp() {
        TestConfig.setToTestInitStateForJsonSchema()
    }

    @Test
    fun testEnumClass() {
        KotlinEnumCodeBuilder(getData())
                .getCode().should.be.equal(getExpectedCode())
    }

    fun getInterceptedClass(): EnumClass? {
        val interceptors = InterceptorManager.getEnabledKotlinDataClassInterceptors()
        var dataClass = json.generateKotlinClass("LogEntry").applyInterceptors(interceptors)
        for(item in dataClass.referencedClasses) {
            if(item is EnumClass) {
                return item
            }
        }
        return null
    }
    override fun getData(): EnumClass {
        var item = getInterceptedClass()
        item.should.not.`null`
        return item  as EnumClass
    }

    override fun getExpectedCode(): String {
        return expectedEnum
    }

    override fun getExpectedCurrentCode(): String {
        return expectedEnum
    }

}