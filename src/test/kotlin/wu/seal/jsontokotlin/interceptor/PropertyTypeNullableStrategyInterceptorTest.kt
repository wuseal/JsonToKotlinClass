package wu.seal.jsontokotlin.interceptor

import com.winterbe.expekt.should
import org.junit.Before
import org.junit.Test
import wu.seal.jsontokotlin.model.ConfigManager
import wu.seal.jsontokotlin.model.PropertyTypeStrategy
import wu.seal.jsontokotlin.generateKotlinDataClass
import wu.seal.jsontokotlin.test.TestConfig

class PropertyTypeNullableStrategyInterceptorTest {

    private val json = """{"c":123,"a":null,"b":3}"""

    @Before
    fun setUp() {
        TestConfig.setToTestInitState()
    }
    @Test
    fun intercept() {

        ConfigManager.propertyTypeStrategy = PropertyTypeStrategy.NotNullable
        val dataClassNoneNullable = json.generateKotlinDataClass().applyInterceptor(PropertyTypeNullableStrategyInterceptor())

        dataClassNoneNullable.getCode().should.be.equal("""data class Test(
    val c: Int, // 123
    val a: Any, // null
    val b: Int // 3
)""")

        ConfigManager.propertyTypeStrategy = PropertyTypeStrategy.Nullable
        val dataClassNullable = json.generateKotlinDataClass().applyInterceptor(PropertyTypeNullableStrategyInterceptor())

        dataClassNullable.getCode().should.be.equal("""data class Test(
    val c: Int?, // 123
    val a: Any?, // null
    val b: Int? // 3
)""")

        ConfigManager.propertyTypeStrategy = PropertyTypeStrategy.AutoDeterMineNullableOrNot
        val dataClassAutoDetermineNullable = json.generateKotlinDataClass().applyInterceptor(PropertyTypeNullableStrategyInterceptor())

        dataClassAutoDetermineNullable.getCode().should.be.equal("""data class Test(
    val c: Int, // 123
    val a: Any?, // null
    val b: Int // 3
)""")
    }
}