package wu.seal.jsontokotlin

import org.junit.Before
import org.junit.Test
import wu.seal.jsontokotlin.model.ConfigManager
import wu.seal.jsontokotlin.model.DefaultValueStrategy
import wu.seal.jsontokotlin.model.PropertyTypeStrategy
import wu.seal.jsontokotlin.model.TargetJsonConverter
import wu.seal.jsontokotlin.test.TestConfig
import kotlin.reflect.KMutableProperty0

class ConfigManagerTestHelper {


    @Before
    fun before() {
        TestConfig.setToTestInitState()
    }


    fun testAllConfigWithAction(action: () -> Unit) {
        testAllBoolConfigsWithAction {
            testAllNoBoolConfigWithAction {
                action()
            }
        }
    }
    fun testAllBoolConfigsWithAction(action: () -> Unit) {

        traverseBoolConfigOnAction(ConfigManager::enableMapType) {
            traverseBoolConfigOnAction(ConfigManager::enableMinimalAnnotation) {
                    traverseBoolConfigOnAction(ConfigManager::isCommentOff) {
                        traverseBoolConfigOnAction(ConfigManager::isInnerClassModel) {
                            traverseBoolConfigOnAction(ConfigManager::isPropertiesVar) {
                                action()
                            }
                        }
                    }
            }
        }
    }

    private fun testAllNoBoolConfigWithAction(action: () -> Unit) {
        traverseConfigOnAction(ConfigManager::indent, listOf(2,4,8)){
            traverseConfigOnAction(ConfigManager::propertyTypeStrategy, PropertyTypeStrategy.values().toList()){
                traverseConfigOnAction(ConfigManager::targetJsonConverterLib, TargetJsonConverter.values().toList()){
                    traverseConfigOnAction(ConfigManager::defaultValueStrategy, DefaultValueStrategy.values().toList()) {
                        action()
                    }
                }
            }
        }
    }
    private fun traverseBoolConfigOnAction(boolConfig: KMutableProperty0<Boolean>, action: () -> Unit) {
        traverseConfigOnAction(boolConfig, listOf(true, false), action)
    }


    private fun <R> traverseConfigOnAction(config: KMutableProperty0<R>, configAbleValues: List<R>, action: () -> Unit) {
        configAbleValues.forEach {
            config.set(it)
            action()
        }
    }


    var color = false
    var weight = false

    @Test
    fun testTraverseBoolConfigOnAction() {
        traverseBoolConfigOnAction(::color) {
            traverseBoolConfigOnAction(::weight) {
                val print = buildString {

                    if (color) {
                        append("color")
                    }

                    if (weight) {
                        append("weight")
                    }

                }
                println(print)

            }

        }
    }

    @Test
    fun testIncludeAction() {
        var action = { println("action") }
        action = combineActions(action, {
            println("Contianer")
        })
        action.invoke()
    }

    @Test
    fun testIncludeRunable() {
        val runable = Runnable { println("runable") }
        val runable1 = Runnable {
            println("Container runable")
            runable.run()
        }

        runable1.run()
    }

    fun combineActions(vararg actions: () -> Unit): () -> Unit {
        return {
            actions.forEach { it.invoke() }
        }
    }
}
