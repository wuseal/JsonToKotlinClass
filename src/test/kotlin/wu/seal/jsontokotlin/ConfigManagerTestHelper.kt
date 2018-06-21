package wu.seal.jsontokotlin

import org.junit.Before
import org.junit.Test
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
                traverseBoolConfigOnAction(ConfigManager::initWithDefaultValue) {
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
    }

    fun testAllNoBoolConfigWithAction(action: () -> Unit) {
        traverseConfigOnAction(ConfigManager::indent, listOf(2,4,8)){
            traverseConfigOnAction(ConfigManager::propertyTypeStrategy, PropertyTypeStrategy.values().toList()){
                traverseConfigOnAction(ConfigManager::targetJsonConverterLib,TargetJsonConverter.values().toList()){
                    action()
                }
            }
        }
    }
    fun traverseBoolConfigOnAction(boolConfig: KMutableProperty0<Boolean>, action: () -> Unit) {
        traverseConfigOnAction(boolConfig, listOf(true, false), action)
    }


    fun <R> traverseConfigOnAction(config: KMutableProperty0<R>, configAbleValues: List<R>, action: () -> Unit) {
        configAbleValues.forEach {
            config.set(it)
            action()
        }
    }

    fun buildBoolAction(configProperties: List<KMutableProperty0<Boolean>>, action: () -> Unit): () -> Unit {

        var newAction = action
        configProperties.forEach {
            val temp = { traverseBoolConfigOnAction(it, newAction) }

            newAction = temp


        }
        return newAction
    }


    fun iterateBoolConfigs(iterator: AbstractIterator<KMutableProperty0<Boolean>>, action: () -> Unit) {
        if (iterator.hasNext()) {
            traverseBoolConfigOnAction(iterator.next(), { iterateBoolConfigs(iterator, action) })
        }
    }

    fun buildAllBoolConfigAction(action: () -> Unit): () -> Unit {

        val boolProperties = listOf(
            ConfigManager::enableAutoReformat,
            ConfigManager::enableMapType,
            ConfigManager::enableMinimalAnnotation,
            ConfigManager::initWithDefaultValue,
            ConfigManager::isCommentOff,
            ConfigManager::isInnerClassModel,
            ConfigManager::isPropertiesVar,
            ConfigManager::isPropertyNullable
        )

        return buildBoolAction(boolProperties, action)
    }

    var color = false
    var weight = false

    //@Test
    fun testBuildBoolAction() {
        val action = buildBoolAction(listOf(::color, ::weight)) {

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

        action()
    }

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
        var runable = Runnable { println("runable") }
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