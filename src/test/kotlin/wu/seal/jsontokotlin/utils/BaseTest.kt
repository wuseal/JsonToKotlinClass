package wu.seal.jsontokotlin.utils

import org.junit.Before
import wu.seal.jsontokotlin.test.TestConfig

open class BaseTest {
    @Before
    fun beforeTest() {
        TestConfig.setToTestInitState()
    }
}