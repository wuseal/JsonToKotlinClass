package wu.seal.jsontokotlin

import wu.seal.jsontokotlin.utils.KotlinClassCodeMaker
import wu.seal.jsontokotlin.utils.KotlinClassMaker

/**
 * Kotlin code maker
 * Created by seal.wu on 2017/8/21.
 */
class KotlinCodeMaker(private val className: String, private val inputJson: String) {

    fun makeKotlinData(): String {
        val kotlinClass = KotlinClassMaker(
                className,
                inputJson
        ).makeKotlinClass()
        return KotlinClassCodeMaker(
                kotlinClass
        ).makeKotlinClassCode()
    }
}
