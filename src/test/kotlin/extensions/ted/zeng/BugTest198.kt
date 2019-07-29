package extensions.ted.zeng

import com.winterbe.expekt.should
import org.junit.Before
import org.junit.Test
import wu.seal.jsontokotlin.ConfigManager
import wu.seal.jsontokotlin.codeelements.KProperty
import wu.seal.jsontokotlin.test.TestConfig

class BugTest198 {
    @Before
    fun setup(){
        TestConfig.isTestModel = true
        ConfigManager.indent = 0
    }
    @Test
    fun propertyNameCheck(){
        val kp = KProperty("X\n\nX","Int","1")
        kp.getPropertyStringBlock().should.equal("val X\\n\\nX: Int")

    }


}