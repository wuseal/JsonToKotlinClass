package wu.seal.jsontokotlin.regression

import com.winterbe.expekt.should
import extensions.chen.biao.KeepAnnotationSupport
import extensions.jose.han.ParcelableAnnotationSupport
import extensions.wu.seal.KeepAnnotationSupportForAndroidX
import org.junit.Test
import wu.seal.jsontokotlin.KotlinCodeMaker
import wu.seal.jsontokotlin.model.TargetJsonConverter
import wu.seal.jsontokotlin.test.TestConfig

class Issue260Test {

    @Test
    fun testIssue260() {
        TestConfig.setToTestInitState()
        TestConfig.targetJsonConvertLib = TargetJsonConverter.MoshiCodeGen
        KeepAnnotationSupport.getTestHelper().setConfig("chen.biao.add_keep_annotation_enable",true.toString())
        val json="""{"a":"yes"}"""
        val expectCode = """
            @Keep
            @JsonClass(generateAdapter = true)
            data class A(
                @Json(name = "a")
                val a: String = "" // yes
            )
            """.trimIndent()
        val resultCode = KotlinCodeMaker("A", json).makeKotlinData()
        resultCode.should.be.equal(expectCode)
    }
    @Test
    fun testIssue260ForAndroidXKeep() {
        TestConfig.setToTestInitState()
        TestConfig.targetJsonConvertLib = TargetJsonConverter.MoshiCodeGen
        KeepAnnotationSupportForAndroidX.getTestHelper().setConfig("wu.seal.add_keep_annotation_enable_androidx",true.toString())
        val json="""{"a":"yes"}"""
        val expectCode = """
            @Keep
            @JsonClass(generateAdapter = true)
            data class A(
                @Json(name = "a")
                val a: String = "" // yes
            )
            """.trimIndent()
        val resultCode = KotlinCodeMaker("A", json).makeKotlinData()
        resultCode.should.be.equal(expectCode)
    }

    @Test
    fun testIssue260ForParcelableSupport() {
        TestConfig.setToTestInitState()
        TestConfig.targetJsonConvertLib = TargetJsonConverter.MoshiCodeGen
        ParcelableAnnotationSupport.getTestHelper().setConfig("jose.han.add_parcelable_annotatioin_enable",true.toString())
        val json="""{"a":"yes"}"""
        val expectCode = """
            @SuppressLint("ParcelCreator")
            @Parcelize
            @JsonClass(generateAdapter = true)
            data class A(
                @Json(name = "a")
                val a: String = "" // yes
            ) : Parcelable
            """.trimIndent()
        val resultCode = KotlinCodeMaker("A", json).makeKotlinData()
        resultCode.should.be.equal(expectCode)
    }
}