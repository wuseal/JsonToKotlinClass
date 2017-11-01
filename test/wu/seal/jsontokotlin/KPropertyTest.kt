package wu.seal.jsontokotlin

import org.junit.*

import wu.seal.jsontokotlin.codeelements.KProperty

/**
 * Created by Seal.Wu on 2017/11/1.
 */
class KPropertyTest {
    @Before
    fun setUp() {
        isTestModel = true
    }

    /**
     * test the config and choose respective supporter logic is OK
     */
    @org.junit.Test
    fun getPropertyStringBlock() {

        TargetJsonConverter.values().forEach {
            ConfigManager.targetJsonConverterLib = it
            val property = KProperty("seal is a *() good_man", "Boolean", "true")


            val propertyStringBlock = property.getPropertyStringBlock()

            when(ConfigManager.targetJsonConverterLib){
                TargetJsonConverter.None-> assert(propertyStringBlock.contains("@").not())
                TargetJsonConverter.Gson-> assert(propertyStringBlock.contains("@SerializedName"))
                TargetJsonConverter.Jackson-> assert(propertyStringBlock.contains("@JsonProperty"))
                TargetJsonConverter.FastJson-> assert(propertyStringBlock.contains("@JSONField(name"))
                TargetJsonConverter.MoShi-> assert(propertyStringBlock.contains("@Json(name"))
                TargetJsonConverter.LoganSquare-> assert(propertyStringBlock.contains("@JsonField(name = arrayOf"))
                TargetJsonConverter.Custom-> assert(propertyStringBlock.contains(ConfigManager.customAnnotaionFormatString.substring(0..1)))
            }

            println("getPropertyStringBlock:\n$propertyStringBlock")
        }

    }

}