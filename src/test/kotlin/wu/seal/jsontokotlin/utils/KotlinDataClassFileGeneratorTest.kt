package wu.seal.jsontokotlin.utils

import com.winterbe.expekt.should
import org.junit.Before
import org.junit.Test
import wu.seal.jsontokotlin.test.TestConfig
import wu.seal.jsontokotlin.utils.classblockparse.ClassCodeParser

class KotlinDataClassFileGeneratorTest {

    val classStrings = """data class Class3(
    val programmers: List<Programmer?>? = listOf(),
    val authors: List<Author?>? = listOf(),
    val musicians: List<Musician?>? = listOf()
)

data class Programmer(
    val isFirstName: String? = "", // Brett
    val lastName: String? = "", // Harold
    val email: String? = "", // cccc
    val firstName: String? = "" // Elliotte
)"""

    val classStrings2 = """data class Class(
    @SerializedName("Class") val classX: Class = Class()
)

data class Class(
    @SerializedName("sa") val sa: Int = 0, // 0
    @SerializedName("Class") val classX: Class = Class()
)

data class Class(
    @SerializedName("ssa") val ssa: String = "" // sdfsf
)"""

    @Before
    fun setUp() {
        TestConfig.setToTestInitState()
    }

    @Test
    fun buildTypeReferenceTest() {

        val classes = getClassesStringList(classStrings).map { ClassCodeParser(it).getKotlinDataClass() }

        val resultBuild = KotlinDataClassFileGenerator().buildTypeReference(classes)
        val property = resultBuild[0].properties[0]

        property.kotlinDataClassPropertyTypeRef.should.be.identity(resultBuild[1])

        val printType = property.propertyType
        val referenceType = getRawType(getChildType(printType))
        referenceType.should.be.equal(property.kotlinDataClassPropertyTypeRef.name)
    }

    @Test
    fun synchronizedPropertyTypeWithTypeRefTest() {
        val classes = getClassesStringList(classStrings).map { ClassCodeParser(it).getKotlinDataClass() }

        val generate = KotlinDataClassFileGenerator()

        val buildTypeList = generate.buildTypeReference(classes)

        val synchronizedList = generate.synchronizedPropertyTypeWithTypeRef(buildTypeList)

        synchronizedList[0].properties[0].propertyType.should.be.equal("List<Programmer?>?")

        println(synchronizedList[0].toString())
    }


    @Test
    fun buildTypeReferenceTest2() {

        val classes = getClassesStringList(classStrings2).map { ClassCodeParser(it).getKotlinDataClass() }

        val resultBuild = KotlinDataClassFileGenerator().buildTypeReference(classes)
        val property = resultBuild[0].properties[0]
        val printType = property.propertyType
        val referenceType = getRawType(getChildType(printType))
        referenceType.should.be.equal(property.kotlinDataClassPropertyTypeRef.name)
    }

    @Test
    fun synchronizedPropertyTypeWithTypeRefTest2() {
        val classes = getClassesStringList(classStrings2).map { ClassCodeParser(it).getKotlinDataClass() }

        val generate = KotlinDataClassFileGenerator()

        val buildTypeList = generate.buildTypeReference(classes)

        /**
         * rename class name
         */
        val newClassNames = listOf("Class","ClassX","ClassXX")

        val newKotlinDataClasses =generate.updateClassNames(buildTypeList,newClassNames)

        val synchronizedList = generate.synchronizedPropertyTypeWithTypeRef(newKotlinDataClasses)

        synchronizedList[0].properties[0].propertyType.should.be.equal("ClassX")
        synchronizedList[0].properties[0].propertyValue.should.be.equal("ClassX()")
        synchronizedList[1].properties[1].propertyType.should.be.equal("ClassXX")
        synchronizedList[1].properties[1].propertyValue.should.be.equal("ClassXX()")

        synchronizedList[0].name.should.be.equal("Class")
        synchronizedList[1].name.should.be.equal("ClassX")
        synchronizedList[2].name.should.be.equal("ClassXX")

        println(synchronizedList[0].toString())
        println(synchronizedList[1].toString())
        println(synchronizedList[2].toString())
    }
}