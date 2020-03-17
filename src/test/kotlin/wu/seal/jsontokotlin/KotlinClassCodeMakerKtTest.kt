package wu.seal.jsontokotlin

import com.winterbe.expekt.should
import org.junit.Before
import org.junit.Test
import wu.seal.jsontokotlin.model.classscodestruct.GenericListClass
import wu.seal.jsontokotlin.model.classscodestruct.DataClass
import wu.seal.jsontokotlin.test.TestConfig
import wu.seal.jsontokotlin.utils.TYPE_STRING

class KotlinClassCodeMakerKtTest {

    @Before
    fun setUp() {
        TestConfig.setToTestInitState()
    }


    @Test
    fun testGetAllUnModifiableRefClassesRecursively() {
        val json = """{a:2,b:3,c:{say:"hello"}}"""
        val dataClass = json.generateKotlinDataClass()
        val splitClasses = dataClass.getAllModifiableClassesRecursivelyIncludeSelf()
        splitClasses.size.should.be.equal(2)
        splitClasses[0].should.instanceof(DataClass::class.java)
        val s0 = splitClasses[0] as DataClass
        s0.properties.size.should.be.equal(3)
        s0.name.should.equal("Test")
        val s1 = splitClasses[1] as DataClass
        s0.properties[2].typeObject.should.equal(s1)
        s1.name.should.equal("C")
        s1.properties.size.should.equal(1)
        s1.properties[0].name.should.be.equal("say")
        s1.properties[0].type.should.be.equal(TYPE_STRING)
    }

    @Test
    fun testResolveNameConflicts1() {
        val json = """
                    {
                      "a": 2,
                      "b": 3,
                      "c": {
                        "say": "hello",
                        "c": {
                          "yes": "world"
                        }
                      }
                    }
"""
        val dataClass = json.generateKotlinDataClass("C").resolveNameConflicts() as DataClass
        dataClass.name.should.be.equal("C")
        dataClass.properties[2].type.should.be.equal("CX")
        val typeObject = dataClass.properties[2].typeObject as DataClass
        typeObject.name.should.be.equal("CX")
        typeObject.properties[1].type.should.be.equal("CXX")
        typeObject.properties[1].typeObject.name.should.be.equal("CXX")
    }

    @Test
    fun testResolveNameConflicts2() {
        val json = """
                    {
                      "a": 2,
                      "b": 3,
                      "c": {
                        "say": "hello",
                        "c": {
                          "yes": "world"
                        }
                      }
                    }
"""
        val dataClass = json.generateKotlinDataClass("A").resolveNameConflicts() as DataClass
        dataClass.name.should.be.equal("A")
        dataClass.properties[2].type.should.be.equal("C")
        val typeObject = dataClass.properties[2].typeObject as DataClass
        typeObject.name.should.be.equal("C")
        typeObject.properties[1].type.should.be.equal("CX")
        typeObject.properties[1].typeObject.name.should.be.equal("CX")
    }

    @Test
    fun testResolveNameConflicts3() {
        val json = """
        {
        "text": "MXCHIP won a prize",
        "id":1234,
        "detail": {
             "comp":"MXCHIP.Inc",
             "from":"Shanghai",
             "focus":"Internet of Things",
             "module":[{"k":"EMW3165"},{"k":"EMW3166"},{"k":"EMW3167"},{"k":"EMW3168"}]
           }
        }
        """.trimIndent()
        val generateKotlinDataClass = json.generateKotlinDataClass("Test")
        val dataClass = generateKotlinDataClass.resolveNameConflicts() as DataClass
        dataClass.run {
            name.should.be.equal("Test")
            properties.size.should.be.equal(3)
            properties[0].run {
                name.should.be.equal("text")
                originJsonValue.should.be.equal("MXCHIP won a prize")
            }
            properties[1].run {
                name.should.be.equal("id")
                originJsonValue.should.be.equal("1234")
            }
            properties[2].run {
                name.should.be.equal("detail")
                type.should.be.equal("Detail")
                originJsonValue.should.be.equal("")
                typeObject.should.not.be.`null`
                (typeObject as DataClass).run {
                    name.should.be.equal("Detail")
                    properties.size.should.be.equal(4)
                    properties[0].run {
                        name.should.be.equal("comp")
                        originJsonValue.should.equal("MXCHIP.Inc")
                    }
                    properties[1].run {
                        name.should.be.equal("from")
                        originJsonValue.should.equal("Shanghai")
                    }
                    properties[2].run {
                        name.should.be.equal("focus")
                        originJsonValue.should.equal("Internet of Things")
                    }
                    properties[3].run {
                        name.should.be.equal("module")
                        originJsonValue.should.equal("")
                        type.should.be.equal("List<Module>")
                        typeObject.should.not.be.`null`
                        ((typeObject as GenericListClass).generic as DataClass).run {
                            name.should.be.equal("Module")
                            properties.size.should.be.equal(1)
                            properties[0].run {
                                name.should.be.equal("k")
                                originJsonValue.should.be.equal("EMW3165")
                                type.should.be.equal(TYPE_STRING)
                            }
                        }
                    }

                }
            }
        }
    }
}