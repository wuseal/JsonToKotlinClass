package wu.seal.jsontokotlin

import com.winterbe.expekt.should
import org.junit.Before
import org.junit.Test
import wu.seal.jsontokotlin.model.classscodestruct.KotlinClass
import wu.seal.jsontokotlin.test.TestConfig
import wu.seal.jsontokotlin.utils.KotlinClassMaker
import wu.seal.jsontokotlin.utils.classgenerator.DataClassGeneratorByJSONSchema

class JsonSchemaGeneratorTest {
    @Before
    fun setUp() {
        TestConfig.setToTestInitStateForJsonSchema()
    }

    @Test
    fun testBasicJsonSchema() {
        val json = """{
  "${"$"}schema": "http://json-schema.org/draft-04/schema#",
  "title": "Product",
  "description": "A product from Acme\u0027s catalog",
  "type": "object",
  "properties": {
    "id": {
      "description": "The unique identifier for a product",
      "type": "integer"
    },
    "name": {
      "description": "Name of the product",
      "type": "string"
    },
    "price": {
      "type": "number",
      "minimum": 0,
      "exclusiveMinimum": true
    },
    "nested": {
      "type": "object",
      "properties": {
        "id": {
          "description": "The unique identifier for a product",
          "type": "integer"
        },
        "name": {
          "description": "Name of the product",
          "type": "string"
        },
        "price": {
          "type": "number",
          "minimum": 0,
          "exclusiveMinimum": true
        }
      },
      "required": ["id", "name"]
    }
  },
  "required": [
    "id",
    "name",
    "price"
  ]
}
    """.trimIndent()
        val expected = """/**
 * A product from Acme's catalog
 */
data class TestData(
    /**
     * The unique identifier for a product
     */
    val id: Int,
    /**
     * Name of the product
     */
    val name: String,
    val price: Double,
    val nested: Nested
)

data class Nested(
    /**
     * The unique identifier for a product
     */
    val id: Int,
    /**
     * Name of the product
     */
    val name: String,
    val price: Double
)
    """.trimIndent()
        val result = json.generateKotlinClassCode("TestData")
        result.trim().should.be.equal(expected)
    }

    @Test
    fun testJsonSchemaNullableTypesSimple() {
        val json = """{
  "${"$"}schema": "http://json-schema.org/draft-04/schema#",
  "title": "SharedUser",
  "type": "object",
  "additionalProperties": false,
  "properties": {
    "userId": {
      "type": [
        "null",
        "string"
      ]
    },
    "allowed": {
      "type": "boolean",
      "description": "Пользователь принял приглашение"
    },
    "readOnly": {
      "type": "boolean",
      "description": "Этот пользователь не имеет права изменять список/заказ (только читать)"
    }
  }
}
    """.trimIndent()

        val expected = """data class SharedUser(
    val userId: String?,
    /**
     * Пользователь принял приглашение
     */
    val allowed: Boolean,
    /**
     * Этот пользователь не имеет права изменять список/заказ (только читать)
     */
    val readOnly: Boolean
)
    """.trimIndent()
        val result = json.generateKotlinClassCode("SharedUser")
        result.trim().should.be.equal(expected)
    }

    @Test
    fun testJsonSchemaJaggedArray() {
        val json = """{
  "${"$"}schema": "http://json-schema.org/draft-04/schema#",
  "title": "JaggedArrayTest",
  "type": "object",
  "additionalProperties": false,
  "properties": {
    "jaggedStringArray": {
      "type": [
        "array",
        "null"
      ],
      "items": {
        "type": "array",
        "items": {
          "type": "string"
        }
      }
    }
  }
}
    """.trimIndent()

        val expected = """
data class JaggedArrayTest(
    val jaggedStringArray: List<List<String>>?
)
    """.trimIndent()

        val result = json.generateKotlinClassCode("")
        result.trim().should.be.equal(expected)
    }

    @Test
    fun testJsonSchemaWithArrayAndRef() {
        val json = """{
  "${"$"}id": "https://example.com/arrays.schema.json",
  "${"$"}schema": "http://json-schema.org/draft-07/schema#",
  "description": "A representation of a person, company, organization, or place",
  "type": "object",
  "properties": {
    "fruits": {
      "type": "array",
      "items": {
        "type": "string"
      }
    },
    "vegetables": {
      "type": "array",
      "items": { "${"$"}ref": "#/definitions/veggie" }
    }
  },
  "definitions": {
    "veggie": {
      "type": "object",
      "required": [ "veggieName", "veggieLike" ],
      "properties": {
        "veggieName": {
          "type": "string",
          "description": "The name of the vegetable."
        },
        "veggieLike": {
          "type": "boolean",
          "description": "Do I like this vegetable?"
        }
      }
    }
  }
}
    """.trimIndent()

        val expected = """/**
 * A representation of a person, company, organization, or place
 */
data class Sample(
    val fruits: List<String>,
    val vegetables: List<veggie>
)

data class veggie(
    /**
     * The name of the vegetable.
     */
    val veggieName: String,
    /**
     * Do I like this vegetable?
     */
    val veggieLike: Boolean
)
    """.trimIndent()

        val result = json.generateKotlinClassCode("Sample")
        result.trim().should.be.equal(expected)
    }

    @Test
    fun testJsonSchemaWithEnumsDefinition() {
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

        val expected = """data class LogEntry(
    val id: String?,
    val timestamp: org.threeten.bp.OffsetDateTime,
    val removed: Boolean,
    /**
     * Тип события
     */
    val typeA: LogEventType,
    /**
     * Подсобытие (набор значений зависит от действия)
     */
    val typeB: Int,
    val ip: String?,
    val userId: String?,
    /**
     * ID устройства из коллекции Devices
     */
    val deviceId: String?,
    val phone: String?,
    val hwid: String?,
    val osUserId: String?
)

enum class LogEventType(val value: Int) {
    Undefined(0),

    Auth(1);
}
    """.trimIndent()

        val result = json.generateKotlinClassCode("LogEntry")
        result.trim().should.be.equal(expected)
    }

    @Test
    fun testJsonSchemaComplicated() {
        val json = """{
  "${"$"}schema": "http://json-schema.org/draft-04/schema#",
  "title": "JsonSerializer",
  "type": "object",
  "description": "Serializes and deserializes objects into and from the JSON format.\nThe JsonSerializer enables you to control how objects are encoded into JSON.",
  "additionalProperties": false,
  "properties": {
    "ReferenceResolver": {
      "description": "Gets or sets the IReferenceResolver used by the serializer when resolving references.",
      "oneOf": [
        {
          "type": "null"
        },
        {
         "${"$"}ref": "#/definitions/IReferenceResolver"
        }
      ]
    },
    "Binder": {
      "description": "Gets or sets the SerializationBinder used by the serializer when resolving type names.",
      "oneOf": [
        {
          "type": "null"
        },
        {
         "${"$"}ref": "#/definitions/SerializationBinder"
        }
      ]
    },
    "SerializationBinder": {
      "description": "Gets or sets the ISerializationBinder used by the serializer when resolving type names.",
      "oneOf": [
        {
          "type": "null"
        },
        {
         "${"$"}ref": "#/definitions/ISerializationBinder"
        }
      ]
    },
    "TraceWriter": {
      "description": "Gets or sets the ITraceWriter used by the serializer when writing trace messages.",
      "oneOf": [
        {
          "type": "null"
        },
        {
         "${"$"}ref": "#/definitions/ITraceWriter"
        }
      ]
    },
    "EqualityComparer": {
      "description": "Gets or sets the equality comparer used by the serializer when comparing references.",
      "oneOf": [
        {
          "type": "null"
        },
        {
         "${"$"}ref": "#/definitions/IEqualityComparer"
        }
      ]
    },
    "TypeNameHandling": {
      "description": "Gets or sets how type name writing and reading is handled by the serializer.\nThe default value is None.",
      "oneOf": [
        {
         "${"$"}ref": "#/definitions/TypeNameHandling"
        }
      ]
    },
    "TypeNameAssemblyFormat": {
      "description": "Gets or sets how a type name assembly is written and resolved by the serializer.\nThe default value is Simple.",
      "oneOf": [
        {
         "${"$"}ref": "#/definitions/FormatterAssemblyStyle"
        }
      ]
    },
    "TypeNameAssemblyFormatHandling": {
      "description": "Gets or sets how a type name assembly is written and resolved by the serializer.\nThe default value is Simple.",
      "oneOf": [
        {
         "${"$"}ref": "#/definitions/TypeNameAssemblyFormatHandling"
        }
      ]
    },
    "PreserveReferencesHandling": {
      "description": "Gets or sets how object references are preserved by the serializer.\nThe default value is None.",
      "oneOf": [
        {
         "${"$"}ref": "#/definitions/PreserveReferencesHandling"
        }
      ]
    },
    "ReferenceLoopHandling": {
      "description": "Gets or sets how reference loops (e.g. a class referencing itself) is handled.\nThe default value is Error.",
      "oneOf": [
        {
         "${"$"}ref": "#/definitions/ReferenceLoopHandling"
        }
      ]
    },
    "MissingMemberHandling": {
      "description": "Gets or sets how missing members (e.g. JSON contains a property that isn't a member on the object) are handled during deserialization.\nThe default value is Ignore.",
      "oneOf": [
        {
         "${"$"}ref": "#/definitions/MissingMemberHandling"
        }
      ]
    },
    "NullValueHandling": {
      "description": "Gets or sets how null values are handled during serialization and deserialization.\nThe default value is Include.",
      "oneOf": [
        {
         "${"$"}ref": "#/definitions/NullValueHandling"
        }
      ]
    },
    "DefaultValueHandling": {
      "description": "Gets or sets how default values are handled during serialization and deserialization.\nThe default value is Include.",
      "oneOf": [
        {
         "${"$"}ref": "#/definitions/DefaultValueHandling"
        }
      ]
    },
    "ObjectCreationHandling": {
      "description": "Gets or sets how objects are created during deserialization.\nThe default value is Auto.",
      "oneOf": [
        {
         "${"$"}ref": "#/definitions/ObjectCreationHandling"
        }
      ]
    },
    "ConstructorHandling": {
      "description": "Gets or sets how constructors are used during deserialization.\nThe default value is Default.",
      "oneOf": [
        {
         "${"$"}ref": "#/definitions/ConstructorHandling"
        }
      ]
    },
    "MetadataPropertyHandling": {
      "description": "Gets or sets how metadata properties are used during deserialization.\nThe default value is Default.",
      "oneOf": [
        {
         "${"$"}ref": "#/definitions/MetadataPropertyHandling"
        }
      ]
    },
    "Converters": {
      "description": "Gets a collection JsonConverter that will be used during serialization.",
      "oneOf": [
        {
          "type": "null"
        },
        {
         "${"$"}ref": "#/definitions/JsonConverterCollection"
        }
      ]
    },
    "ContractResolver": {
      "description": "Gets or sets the contract resolver used by the serializer when\nserializing .NET objects to JSON and vice versa.",
      "oneOf": [
        {
          "type": "null"
        },
        {
         "${"$"}ref": "#/definitions/IContractResolver"
        }
      ]
    },
    "Context": {
      "description": "Gets or sets the StreamingContext used by the serializer when invoking serialization callback methods.",
      "oneOf": [
        {
         "${"$"}ref": "#/definitions/StreamingContext"
        }
      ]
    },
    "Formatting": {
      "description": "Indicates how JSON text output is formatted.\nThe default value is None.",
      "oneOf": [
        {
         "${"$"}ref": "#/definitions/Formatting"
        }
      ]
    },
    "DateFormatHandling": {
      "description": "Gets or sets how dates are written to JSON text.\nThe default value is IsoDateFormat.",
      "oneOf": [
        {
         "${"$"}ref": "#/definitions/DateFormatHandling"
        }
      ]
    },
    "DateTimeZoneHandling": {
      "description": "Gets or sets how DateTime time zones are handled during serialization and deserialization.\nThe default value is RoundtripKind.",
      "oneOf": [
        {
         "${"$"}ref": "#/definitions/DateTimeZoneHandling"
        }
      ]
    },
    "DateParseHandling": {
      "description": "Gets or sets how date formatted strings, e.g. \"\\/Date(1198908717056)\\/\" and \"2012-03-21T05:40Z\", are parsed when reading JSON.\nThe default value is DateTime.",
      "oneOf": [
        {
         "${"$"}ref": "#/definitions/DateParseHandling"
        }
      ]
    },
    "FloatParseHandling": {
      "description": "Gets or sets how floating point numbers, e.g. 1.0 and 9.9, are parsed when reading JSON text.\nThe default value is Double.",
      "oneOf": [
        {
         "${"$"}ref": "#/definitions/FloatParseHandling"
        }
      ]
    },
    "FloatFormatHandling": {
      "description": "Gets or sets how special floating point numbers, e.g. NaN,\nPositiveInfinity and NegativeInfinity,\nare written as JSON text.\nThe default value is String.",
      "oneOf": [
        {
         "${"$"}ref": "#/definitions/FloatFormatHandling"
        }
      ]
    },
    "StringEscapeHandling": {
      "description": "Gets or sets how strings are escaped when writing JSON text.\nThe default value is Default.",
      "oneOf": [
        {
         "${"$"}ref": "#/definitions/StringEscapeHandling"
        }
      ]
    },
    "DateFormatString": {
      "type": [
        "null",
        "string"
      ],
      "description": "Gets or sets how DateTime and DateTimeOffset values are formatted when writing JSON text,\nand the expected date format when reading JSON text.\nThe default value is \"yyyy'-'MM'-'dd'T'HH':'mm':'ss.FFFFFFFK\"."
    },
    "Culture": {
      "type": [
        "null",
        "string"
      ],
      "description": "Gets or sets the culture used when reading JSON.\nThe default value is InvariantCulture."
    },
    "MaxDepth": {
      "type": [
        "integer",
        "null"
      ],
      "description": "Gets or sets the maximum depth allowed when reading JSON. Reading past this depth will throw a JsonReaderException.\nA null value means there is no maximum.\nThe default value is null.",
      "format": "int32"
    },
    "CheckAdditionalContent": {
      "type": "boolean",
      "description": "Gets a value indicating whether there will be a check for additional JSON content after deserializing an object.\nThe default value is false."
    }
  },
  "definitions": {
    "IReferenceResolver": {
      "type": "object",
      "description": "Used to resolve references when serializing and deserializing JSON by the JsonSerializer.",
      "x-abstract": true,
      "additionalProperties": false
    },
    "SerializationBinder": {
      "type": "object",
      "x-abstract": true,
      "additionalProperties": false
    },
    "ISerializationBinder": {
      "type": "object",
      "description": "Allows users to control class loading and mandate what class to load.",
      "x-abstract": true,
      "additionalProperties": false
    },
    "ITraceWriter": {
      "type": "object",
      "description": "Represents a trace writer.",
      "x-abstract": true,
      "additionalProperties": false,
      "properties": {
        "LevelFilter": {
          "description": "Gets the TraceLevel that will be used to filter the trace messages passed to the writer.\nFor example a filter level of Info will exclude Verbose messages and include Info,\nWarning and Error messages.",
          "oneOf": [
            {
             "${"$"}ref": "#/definitions/TraceLevel"
            }
          ]
        }
      }
    },
    "TraceLevel": {
      "type": "integer",
      "description": "",
      "x-enumNames": [
        "Off",
        "Error",
        "Warning",
        "Info",
        "Verbose"
      ],
      "enum": [
        0,
        1,
        2,
        3,
        4
      ]
    },
    "IEqualityComparer": {
      "type": "object",
      "x-abstract": true,
      "additionalProperties": false
    },
    "TypeNameHandling": {
      "type": "integer",
      "description": "Specifies type name handling options for the JsonSerializer.",
      "x-enumFlags": true,
      "x-enumNames": [
        "None",
        "Objects",
        "Arrays",
        "All",
        "Auto"
      ],
      "enum": [
        0,
        1,
        2,
        3,
        4
      ]
    },
    "FormatterAssemblyStyle": {
      "type": "integer",
      "description": "",
      "x-enumNames": [
        "Simple",
        "Full"
      ],
      "enum": [
        0,
        1
      ]
    },
    "TypeNameAssemblyFormatHandling": {
      "type": "integer",
      "description": "Indicates the method that will be used during deserialization for locating and loading assemblies.",
      "x-enumNames": [
        "Simple",
        "Full"
      ],
      "enum": [
        0,
        1
      ]
    },
    "PreserveReferencesHandling": {
      "type": "integer",
      "description": "Specifies reference handling options for the JsonSerializer.\nNote that references cannot be preserved when a value is set via a non-default constructor such as types that implement ISerializable.",
      "x-enumFlags": true,
      "x-enumNames": [
        "None",
        "Objects",
        "Arrays",
        "All"
      ],
      "enum": [
        0,
        1,
        2,
        3
      ]
    },
    "ReferenceLoopHandling": {
      "type": "integer",
      "description": "Specifies reference loop handling options for the JsonSerializer.",
      "x-enumNames": [
        "Error",
        "Ignore",
        "Serialize"
      ],
      "enum": [
        0,
        1,
        2
      ]
    },
    "MissingMemberHandling": {
      "type": "integer",
      "description": "Specifies missing member handling options for the JsonSerializer.",
      "x-enumNames": [
        "Ignore",
        "Error"
      ],
      "enum": [
        0,
        1
      ]
    },
    "NullValueHandling": {
      "type": "integer",
      "description": "Specifies null value handling options for the JsonSerializer.",
      "x-enumNames": [
        "Include",
        "Ignore"
      ],
      "enum": [
        0,
        1
      ]
    },
    "DefaultValueHandling": {
      "type": "integer",
      "description": "Specifies default value handling options for the JsonSerializer.",
      "x-enumFlags": true,
      "x-enumNames": [
        "Include",
        "Ignore",
        "Populate",
        "IgnoreAndPopulate"
      ],
      "enum": [
        0,
        1,
        2,
        3
      ]
    },
    "ObjectCreationHandling": {
      "type": "integer",
      "description": "Specifies how object creation is handled by the JsonSerializer.",
      "x-enumNames": [
        "Auto",
        "Reuse",
        "Replace"
      ],
      "enum": [
        0,
        1,
        2
      ]
    },
    "ConstructorHandling": {
      "type": "integer",
      "description": "Specifies how constructors are used when initializing objects during deserialization by the JsonSerializer.",
      "x-enumNames": [
        "Default",
        "AllowNonPublicDefaultConstructor"
      ],
      "enum": [
        0,
        1
      ]
    },
    "MetadataPropertyHandling": {
      "type": "integer",
      "description": "Specifies metadata property handling options for the JsonSerializer.",
      "x-enumNames": [
        "Default",
        "ReadAhead",
        "Ignore"
      ],
      "enum": [
        0,
        1,
        2
      ]
    },
    "JsonConverterCollection": {
      "type": "array",
      "description": "Represents a collection of JsonConverter.",
      "items": {
       "${"$"}ref": "#/definitions/JsonConverter"
      }
    },
    "JsonConverter": {
      "type": "object",
      "description": "Converts an object to and from JSON.",
      "x-abstract": true,
      "additionalProperties": false,
      "properties": {
        "CanRead": {
          "type": "boolean",
          "description": "Gets a value indicating whether this JsonConverter can read JSON."
        },
        "CanWrite": {
          "type": "boolean",
          "description": "Gets a value indicating whether this JsonConverter can write JSON."
        }
      }
    },
    "IContractResolver": {
      "type": "object",
      "description": "Used by JsonSerializer to resolve a JsonContract for a given Type.",
      "x-abstract": true,
      "additionalProperties": false
    },
    "StreamingContext": {
      "type": "object",
      "additionalProperties": false,
      "properties": {
        "State": {
         "${"$"}ref": "#/definitions/StreamingContextStates"
        },
        "Context": {
          "oneOf": [
            {},
            {
              "type": "null"
            }
          ]
        }
      }
    },
    "StreamingContextStates": {
      "type": "integer",
      "description": "",
      "x-enumFlags": true,
      "x-enumNames": [
        "CrossProcess",
        "CrossMachine",
        "File",
        "Persistence",
        "Remoting",
        "Other",
        "Clone",
        "CrossAppDomain",
        "All"
      ],
      "enum": [
        1,
        2,
        4,
        8,
        16,
        32,
        64,
        128,
        255
      ]
    },
    "Formatting": {
      "type": "integer",
      "description": "Specifies formatting options for the JsonTextWriter.",
      "x-enumNames": [
        "None",
        "Indented"
      ],
      "enum": [
        0,
        1
      ]
    },
    "DateFormatHandling": {
      "type": "integer",
      "description": "Specifies how dates are formatted when writing JSON text.",
      "x-enumNames": [
        "IsoDateFormat",
        "MicrosoftDateFormat"
      ],
      "enum": [
        0,
        1
      ]
    },
    "DateTimeZoneHandling": {
      "type": "integer",
      "description": "Specifies how to treat the time value when converting between string and DateTime.",
      "x-enumNames": [
        "Local",
        "Utc",
        "Unspecified",
        "RoundtripKind"
      ],
      "enum": [
        0,
        1,
        2,
        3
      ]
    },
    "DateParseHandling": {
      "type": "integer",
      "description": "Specifies how date formatted strings, e.g. \"\\/Date(1198908717056)\\/\" and \"2012-03-21T05:40Z\", are parsed when reading JSON text.",
      "x-enumNames": [
        "None",
        "DateTime",
        "DateTimeOffset"
      ],
      "enum": [
        0,
        1,
        2
      ]
    },
    "FloatParseHandling": {
      "type": "integer",
      "description": "Specifies how floating point numbers, e.g. 1.0 and 9.9, are parsed when reading JSON text.",
      "x-enumNames": [
        "Double",
        "Decimal"
      ],
      "enum": [
        0,
        1
      ]
    },
    "FloatFormatHandling": {
      "type": "integer",
      "description": "Specifies float format handling options when writing special floating point numbers, e.g. NaN,\nPositiveInfinity and NegativeInfinity with JsonWriter.",
      "x-enumNames": [
        "String",
        "Symbol",
        "DefaultValue"
      ],
      "enum": [
        0,
        1,
        2
      ]
    },
    "StringEscapeHandling": {
      "type": "integer",
      "description": "Specifies how strings are escaped when writing JSON text.",
      "x-enumNames": [
        "Default",
        "EscapeNonAscii",
        "EscapeHtml"
      ],
      "enum": [
        0,
        1,
        2
      ]
    }
  }
}
    """.trimIndent()

        val expected = """
            /**
             * Serializes and deserializes objects into and from the JSON format.
            The JsonSerializer enables you to control how objects are encoded into JSON.
             */
            data class JsonSerializer(
                /**
                 * Gets or sets the IReferenceResolver used by the serializer when resolving references.
                 */
                val ReferenceResolver: IReferenceResolver?,
                /**
                 * Gets or sets the SerializationBinder used by the serializer when resolving type names.
                 */
                val Binder: SerializationBinder?,
                /**
                 * Gets or sets the ISerializationBinder used by the serializer when resolving type names.
                 */
                val SerializationBinder: ISerializationBinder?,
                /**
                 * Gets or sets the ITraceWriter used by the serializer when writing trace messages.
                 */
                val TraceWriter: ITraceWriter?,
                /**
                 * Gets or sets the equality comparer used by the serializer when comparing references.
                 */
                val EqualityComparer: IEqualityComparer?,
                /**
                 * Gets or sets how type name writing and reading is handled by the serializer.The default value is None.
                 */
                val TypeNameHandling: TypeNameHandling,
                /**
                 * Gets or sets how a type name assembly is written and resolved by the serializer.The default value is Simple.
                 */
                val TypeNameAssemblyFormat: FormatterAssemblyStyle,
                /**
                 * Gets or sets how a type name assembly is written and resolved by the serializer.The default value is Simple.
                 */
                val TypeNameAssemblyFormatHandling: TypeNameAssemblyFormatHandling,
                /**
                 * Gets or sets how object references are preserved by the serializer.The default value is None.
                 */
                val PreserveReferencesHandling: PreserveReferencesHandling,
                /**
                 * Gets or sets how reference loops (e.g. a class referencing itself) is handled.The default value is Error.
                 */
                val ReferenceLoopHandling: ReferenceLoopHandling,
                /**
                 * Gets or sets how missing members (e.g. JSON contains a property that isn't a member on the object) are handled during deserialization.The default value is Ignore.
                 */
                val MissingMemberHandling: MissingMemberHandling,
                /**
                 * Gets or sets how null values are handled during serialization and deserialization.The default value is Include.
                 */
                val NullValueHandling: NullValueHandling,
                /**
                 * Gets or sets how default values are handled during serialization and deserialization.The default value is Include.
                 */
                val DefaultValueHandling: DefaultValueHandling,
                /**
                 * Gets or sets how objects are created during deserialization.The default value is Auto.
                 */
                val ObjectCreationHandling: ObjectCreationHandling,
                /**
                 * Gets or sets how constructors are used during deserialization.The default value is Default.
                 */
                val ConstructorHandling: ConstructorHandling,
                /**
                 * Gets or sets how metadata properties are used during deserialization.The default value is Default.
                 */
                val MetadataPropertyHandling: MetadataPropertyHandling,
                /**
                 * Gets a collection JsonConverter that will be used during serialization.
                 */
                val Converters: JsonConverterCollection?,
                /**
                 * Gets or sets the contract resolver used by the serializer whenserializing .NET objects to JSON and vice versa.
                 */
                val ContractResolver: IContractResolver?,
                /**
                 * Gets or sets the StreamingContext used by the serializer when invoking serialization callback methods.
                 */
                val Context: StreamingContext,
                /**
                 * Indicates how JSON text output is formatted.The default value is None.
                 */
                val Formatting: Formatting,
                /**
                 * Gets or sets how dates are written to JSON text.The default value is IsoDateFormat.
                 */
                val DateFormatHandling: DateFormatHandling,
                /**
                 * Gets or sets how DateTime time zones are handled during serialization and deserialization.The default value is RoundtripKind.
                 */
                val DateTimeZoneHandling: DateTimeZoneHandling,
                /**
                 * Gets or sets how date formatted strings, e.g. "\/Date(1198908717056)\/" and "2012-03-21T05:40Z", are parsed when reading JSON.The default value is DateTime.
                 */
                val DateParseHandling: DateParseHandling,
                /**
                 * Gets or sets how floating point numbers, e.g. 1.0 and 9.9, are parsed when reading JSON text.The default value is Double.
                 */
                val FloatParseHandling: FloatParseHandling,
                /**
                 * Gets or sets how special floating point numbers, e.g. NaN,PositiveInfinity and NegativeInfinity,are written as JSON text.The default value is String.
                 */
                val FloatFormatHandling: FloatFormatHandling,
                /**
                 * Gets or sets how strings are escaped when writing JSON text.The default value is Default.
                 */
                val StringEscapeHandling: StringEscapeHandling,
                /**
                 * Gets or sets how DateTime and DateTimeOffset values are formatted when writing JSON text,and the expected date format when reading JSON text.The default value is "yyyy'-'MM'-'dd'T'HH':'mm':'ss.FFFFFFFK".
                 */
                val DateFormatString: String?,
                /**
                 * Gets or sets the culture used when reading JSON.The default value is InvariantCulture.
                 */
                val Culture: String?,
                /**
                 * Gets or sets the maximum depth allowed when reading JSON. Reading past this depth will throw a JsonReaderException.A null value means there is no maximum.The default value is null.
                 */
                val MaxDepth: Int?,
                /**
                 * Gets a value indicating whether there will be a check for additional JSON content after deserializing an object.The default value is false.
                 */
                val CheckAdditionalContent: Boolean
            )







            /**
             * Represents a trace writer.
             */
            data class ITraceWriter(
                /**
                 * Gets the TraceLevel that will be used to filter the trace messages passed to the writer.For example a filter level of Info will exclude Verbose messages and include Info,Warning and Error messages.
                 */
                val LevelFilter: TraceLevel
            )



            /**
             * Specifies type name handling options for the JsonSerializer.
             */
            enum class TypeNameHandling(val value: Int) {
                None(0),

                Objects(1),

                Arrays(2),

                All(3),

                Auto(4);
            }

            enum class FormatterAssemblyStyle(val value: Int) {
                Simple(0),

                Full(1);
            }

            /**
             * Indicates the method that will be used during deserialization for locating and loading assemblies.
             */
            enum class TypeNameAssemblyFormatHandling(val value: Int) {
                Simple(0),

                Full(1);
            }

            /**
             * Specifies reference handling options for the JsonSerializer.
            Note that references cannot be preserved when a value is set via a non-default constructor such as types that implement ISerializable.
             */
            enum class PreserveReferencesHandling(val value: Int) {
                None(0),

                Objects(1),

                Arrays(2),

                All(3);
            }

            /**
             * Specifies reference loop handling options for the JsonSerializer.
             */
            enum class ReferenceLoopHandling(val value: Int) {
                Error(0),

                Ignore(1),

                Serialize(2);
            }

            /**
             * Specifies missing member handling options for the JsonSerializer.
             */
            enum class MissingMemberHandling(val value: Int) {
                Ignore(0),

                Error(1);
            }

            /**
             * Specifies null value handling options for the JsonSerializer.
             */
            enum class NullValueHandling(val value: Int) {
                Include(0),

                Ignore(1);
            }

            /**
             * Specifies default value handling options for the JsonSerializer.
             */
            enum class DefaultValueHandling(val value: Int) {
                Include(0),

                Ignore(1),

                Populate(2),

                IgnoreAndPopulate(3);
            }

            /**
             * Specifies how object creation is handled by the JsonSerializer.
             */
            enum class ObjectCreationHandling(val value: Int) {
                Auto(0),

                Reuse(1),

                Replace(2);
            }

            /**
             * Specifies how constructors are used when initializing objects during deserialization by the JsonSerializer.
             */
            enum class ConstructorHandling(val value: Int) {
                Default(0),

                AllowNonPublicDefaultConstructor(1);
            }

            /**
             * Specifies metadata property handling options for the JsonSerializer.
             */
            enum class MetadataPropertyHandling(val value: Int) {
                Default(0),

                ReadAhead(1),

                Ignore(2);
            }





            data class StreamingContext(
                val State: StreamingContextStates,
                val Context: Any?
            )

            /**
             * Specifies formatting options for the JsonTextWriter.
             */
            enum class Formatting(val value: Int) {
                None(0),

                Indented(1);
            }

            /**
             * Specifies how dates are formatted when writing JSON text.
             */
            enum class DateFormatHandling(val value: Int) {
                IsoDateFormat(0),

                MicrosoftDateFormat(1);
            }

            /**
             * Specifies how to treat the time value when converting between string and DateTime.
             */
            enum class DateTimeZoneHandling(val value: Int) {
                Local(0),

                Utc(1),

                Unspecified(2),

                RoundtripKind(3);
            }

            /**
             * Specifies how date formatted strings, e.g. "\/Date(1198908717056)\/" and "2012-03-21T05:40Z", are parsed when reading JSON text.
             */
            enum class DateParseHandling(val value: Int) {
                None(0),

                DateTime(1),

                DateTimeOffset(2);
            }

            /**
             * Specifies how floating point numbers, e.g. 1.0 and 9.9, are parsed when reading JSON text.
             */
            enum class FloatParseHandling(val value: Int) {
                Double(0),

                Decimal(1);
            }

            /**
             * Specifies float format handling options when writing special floating point numbers, e.g. NaN,
            PositiveInfinity and NegativeInfinity with JsonWriter.
             */
            enum class FloatFormatHandling(val value: Int) {
                String(0),

                Symbol(1),

                DefaultValue(2);
            }

            /**
             * Specifies how strings are escaped when writing JSON text.
             */
            enum class StringEscapeHandling(val value: Int) {
                Default(0),

                EscapeNonAscii(1),

                EscapeHtml(2);
            }

            enum class TraceLevel(val value: Int) {
                Off(0),

                Error(1),

                Warning(2),

                Info(3),

                Verbose(4);
            }

            enum class StreamingContextStates(val value: Int) {
                CrossProcess(1),

                CrossMachine(2),

                File(4),

                Persistence(8),

                Remoting(16),

                Other(32),

                Clone(64),

                CrossAppDomain(128),

                All(255);
            }
        """.trimIndent()

        val result = json.generateKotlinClassCode("")
        result.trim().should.be.equal(expected)
    }

}

