package wu.seal.jsontokotlin.model.codeelements

import com.winterbe.expekt.should

/**
 *
 * Created by Seal.Wu on 2018/2/6.
 */
class DefaultValueKtTest {
    @org.junit.Test
    fun getDefaultValue() {
        getDefaultValue("String").should.be.equal("\"\"")
        getDefaultValue("Double").should.be.equal("0.0")
        getDefaultValue("Boolean").should.be.equal("false")
        getDefaultValue("Int").should.be.equal("0")
        getDefaultValue("Long").should.be.equal("0")
        getDefaultValue("Any").should.be.equal("Any()")
        getDefaultValue("AList").should.be.equal("AList()")

    }

}
