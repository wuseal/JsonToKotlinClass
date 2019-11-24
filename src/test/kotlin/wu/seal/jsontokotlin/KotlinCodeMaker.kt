package wu.seal.jsontokotlin

/**
 * Kotlin code maker
 * Created by seal.wu on 2017/8/21.
 */
class KotlinCodeMaker(private val className: String, private val inputJson: String) {

    fun makeKotlinData(): String {
        val kotlinClass = KotlinClassMaker(
                className,
                inputJson
        ).makeKotlinDataClass()
        return KotlinClassCodeMaker(
                kotlinClass
        ).makeKotlinClassCode()
    }
}
