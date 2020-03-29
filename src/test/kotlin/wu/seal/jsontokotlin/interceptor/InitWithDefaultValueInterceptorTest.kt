package wu.seal.jsontokotlin.interceptor


import com.winterbe.expekt.should
import org.junit.Before
import org.junit.Test
import wu.seal.jsontokotlin.model.DefaultValueStrategy
import wu.seal.jsontokotlin.model.PropertyTypeStrategy
import wu.seal.jsontokotlin.generateKotlinDataClass
import wu.seal.jsontokotlin.test.TestConfig

class InitWithDefaultValueInterceptorTest {

    private val json = """{"a":123, "b":null}"""

    private val expectedAvoidNull = """data class Test(
    val a: Int = 0, // 123
    val b: Any? = Any() // null
)"""
    private val expectedAllowNull = """data class Test(
    val a: Int = 0, // 123
    val b: Any? = null // null
)"""

    @Before
    fun setUp() {
        TestConfig.setToTestInitState()
    }

    @Test
    fun intercept() {
        TestConfig.propertyTypeStrategy = PropertyTypeStrategy.AutoDeterMineNullableOrNot
        TestConfig.defaultValueStrategy = DefaultValueStrategy.AvoidNull
        var dataClass1 = json.generateKotlinDataClass().applyInterceptors(
                listOf(PropertyTypeNullableStrategyInterceptor(), InitWithDefaultValueInterceptor())
        )
        TestConfig.defaultValueStrategy = DefaultValueStrategy.AllowNull
        var dataClass2 = json.generateKotlinDataClass().applyInterceptors(
                listOf(PropertyTypeNullableStrategyInterceptor(), InitWithDefaultValueInterceptor())
        )
        dataClass1.getCode().should.be.equal(expectedAvoidNull)
        dataClass2.getCode().should.be.equal(expectedAllowNull)
    }
}