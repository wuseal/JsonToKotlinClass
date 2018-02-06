package wu.seal.jsontokotlin.gson

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.winterbe.expekt.should
import org.junit.Test

/**
 *
 * Created by Seal.Wu on 2018/2/6.
 */
class JsonSerialConvention {


    @Test
    fun convertWithUnderScore() {
        val gson = GsonBuilder().apply { setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES) }.create()
        val result = gson.toJson(TestData())
        result.should.contain("under_name")
        val convertObj = gson.fromJson<TestData>(result,TestData::class.java)
        convertObj.underName.should.be.equal("seal")
    }


    private class TestData(val underName:String = "seal")
}