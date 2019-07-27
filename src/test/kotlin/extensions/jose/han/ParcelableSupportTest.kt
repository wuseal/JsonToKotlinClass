package extensions.jose.han

import com.intellij.ui.layout.panel
import com.winterbe.expekt.should
import extensions.Extension
import extensions.chen.biao.KeepAnnotationSupport
import org.junit.Before
import org.junit.Test
import wu.seal.jsontokotlin.classscodestruct.KotlinDataClass
import wu.seal.jsontokotlin.classscodestruct.Method
import wu.seal.jsontokotlin.classscodestruct.Property
import wu.seal.jsontokotlin.generateKotlinDataClass
import wu.seal.jsontokotlin.test.TestConfig
import wu.seal.jsontokotlin.utils.LogUtil
import javax.swing.JCheckBox
import javax.swing.JPanel

class ParcelableSupportTest{
    val json = """{"name":"jose.han","age":18,"height":18.7, "face":true}"""


    var expectResult = """data class Test(
    val name: String, // jose.han
    val age: Int, // 18
    val height: Double, // 18.7
    val face: Boolean // true
) : Parcelable{
    constructor(source: Parcel) : this(
            source.readString(),
            source.readInt(),
            source.readDouble(),
            1 == source.readInt()
    )
    override fun describeContents() = 0
    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(name)
        writeInt(age)
        writeDouble(height)
        writeInt((if (face) 1 else 0))
    }
    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Test> = object:Parcelable.Creator<Test>{
            override fun createFromParcel(source: Parcel): Test = Test(source)
            override fun newArray(size: Int): Array<Test?> = arrayOfNulls(size)
        }
    }
}"""

    @Before
    fun setUp() {
        TestConfig.setToTestInitState()
    }

    @Test
    fun interceptTest(){
        val kotlinDataClass = json.generateKotlinDataClass()
        kotlinDataClass.copy(parentClassTemplate = "Parcelable")
        ParcelableSupport.getTestHelper().setConfig("jose.han.add_parcelable_enable","true")
        val generatedCode = kotlinDataClass.applyInterceptors(listOf(ParcelableSupport)).getCode()
        print(generatedCode)
        generatedCode.trimMargin().should.equal(expectResult.trimMargin())

    }


}