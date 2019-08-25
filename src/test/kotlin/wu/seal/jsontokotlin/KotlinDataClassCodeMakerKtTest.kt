package wu.seal.jsontokotlin

import com.winterbe.expekt.should
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import wu.seal.jsontokotlin.test.TestConfig
import wu.seal.jsontokotlin.utils.TYPE_STRING

class KotlinDataClassCodeMakerKtTest {

    @Before
    fun setUp() {
        TestConfig.setToTestInitState()
    }


    @Test
    fun getSplitClasses() {
        val json = """{a:2,b:3,c:{say:"hello"}}"""
        val dataClass = json.generateKotlinDataClass()
        val splitClasses = dataClass.getSplitClasses()
        splitClasses.size.should.be.equal(2)
        splitClasses[0].properties.size.should.be.equal(3)
        splitClasses[0].name.should.equal("Test")
        splitClasses[0].properties[2].typeObject.should.equal(splitClasses[1])
        splitClasses[1].name.should.equal("C")
        splitClasses[1].properties.size.should.equal(1)
        splitClasses[1].properties[0].name.should.be.equal("say")
        splitClasses[1].properties[0].type.should.be.equal(TYPE_STRING)
    }

    @Test
    fun resolveInnerConflictClassName() {
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
        val dataClass = json.generateKotlinDataClass("C").resolveInnerConflictClassName()
        dataClass.name.should.be.equal("C")
        dataClass.properties[2].type.should.be.equal("CX")
        dataClass.properties[2].typeObject!!.name.should.be.equal("CX")
        dataClass.properties[2].typeObject!!.properties[1].type.should.be.equal("CXX")
        dataClass.properties[2].typeObject!!.properties[1].typeObject!!.name.should.be.equal("CXX")
    }

    @Test
    fun resolveInnerConflictClassName1() {
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
        val dataClass = json.generateKotlinDataClass("A").resolveInnerConflictClassName()
        dataClass.name.should.be.equal("A")
        dataClass.properties[2].type.should.be.equal("C")
        dataClass.properties[2].typeObject!!.name.should.be.equal("C")
        dataClass.properties[2].typeObject!!.properties[1].type.should.be.equal("CX")
        dataClass.properties[2].typeObject!!.properties[1].typeObject!!.name.should.be.equal("CX")
    }
}