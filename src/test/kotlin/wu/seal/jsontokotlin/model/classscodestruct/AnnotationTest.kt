package wu.seal.jsontokotlin.model.classscodestruct

import com.winterbe.expekt.should
import org.junit.Before
import org.junit.Test
import wu.seal.jsontokotlin.model.ConfigManager
import wu.seal.jsontokotlin.model.codeannotations.CustomPropertyAnnotationTemplate
import wu.seal.jsontokotlin.model.codeannotations.FastjsonPropertyAnnotationTemplate
import wu.seal.jsontokotlin.model.codeannotations.GsonPropertyAnnotationTemplate
import wu.seal.jsontokotlin.model.codeannotations.LoganSquarePropertyAnnotationTemplate
import wu.seal.jsontokotlin.test.TestConfig

class AnnotationTest {

    @Before
    fun setUp() {
        TestConfig.setToTestInitState()
    }

    @Test
    fun getAnnotationString() {
        var annotationTemplate = "@SealWu"
        val rawName = "seal king"
        Annotation(annotationTemplate, rawName).getAnnotationString().should.be.equal("@SealWu")
        annotationTemplate = "@SealWu(\"%s\")"
        Annotation(annotationTemplate, rawName).getAnnotationString().should.be.equal("@SealWu(\"seal king\")")
        annotationTemplate = "@SealWu(\"%s\",\"%s\")"
        Annotation(annotationTemplate, rawName).getAnnotationString()
            .should.be.equal("@SealWu(\"seal king\",\"seal king\")")
    }


    @Test
    fun fromAnnotationString() {
        val rawName = "seal is a boy"
        val gsonAnnotationCode = GsonPropertyAnnotationTemplate(rawName).getCode()
        val gsonAnnotation = Annotation.fromAnnotationString(gsonAnnotationCode)
        gsonAnnotation.annotationTemplate.should.be.equal(GsonPropertyAnnotationTemplate.propertyAnnotationFormat)
        gsonAnnotation.rawName.should.be.equal(rawName)

        val fastjsonAnnotationCode = FastjsonPropertyAnnotationTemplate(rawName).getCode()
        val fastjsonAnnotation = Annotation.fromAnnotationString(fastjsonAnnotationCode)
        fastjsonAnnotation.annotationTemplate.should.be.equal(FastjsonPropertyAnnotationTemplate.propertyAnnotationFormat)
        fastjsonAnnotation.rawName.should.be.equal(rawName)


        val logansquareAnnotationCode = LoganSquarePropertyAnnotationTemplate(rawName).getCode()
        val logansquareAnnotation = Annotation.fromAnnotationString(logansquareAnnotationCode)
        logansquareAnnotation.annotationTemplate.should.be.equal(LoganSquarePropertyAnnotationTemplate.propertyAnnotationFormat)
        logansquareAnnotation.rawName.should.be.equal(rawName)

        val customAnnotationCode = CustomPropertyAnnotationTemplate(rawName).getCode()
        val customAnnotation = Annotation.fromAnnotationString(customAnnotationCode)
        customAnnotation.annotationTemplate.should.be.equal(ConfigManager.customPropertyAnnotationFormatString)
        customAnnotation.rawName.should.be.equal(rawName)


        val annotationTemplate = """@Seal(name = "seal" ,who = "%s" ,comment = "%s")"""
        ConfigManager.customPropertyAnnotationFormatString = annotationTemplate
        val customFlexAnnotationCode = Annotation(annotationTemplate, rawName).getAnnotationString()
        val parsedAnnotation = Annotation.fromAnnotationString(customFlexAnnotationCode)
        parsedAnnotation.rawName.should.be.equal(rawName)
        parsedAnnotation.annotationTemplate.should.be.equal(annotationTemplate)


    }

    @Test
    fun fromAnnotationStringOthers() {
        val annotationString = "@SerializedName(\"Email\", default = \"Email\")"
        ConfigManager.customPropertyAnnotationFormatString ="@SerializedName(\"%s\", default = \"%s\")"
        val annotation = Annotation.fromAnnotationString(annotationString)
        annotation.rawName.should.be.equal("Email")
        annotation.annotationTemplate.should.be.equal("@SerializedName(\"%s\", default = \"%s\")")
    }
}
