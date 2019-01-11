package wu.seal.jsontokotlin.utils

import wu.seal.jsontokotlin.ConfigManager

/**
 * Class Code Filter
 * Created by Seal.Wu on 2018/4/18.
 */
object ClassCodeFilter {

    /**
     * when not in `innerClassModel` and the class spit with `\n\n` then remove the duplicate class
     */
    fun removeDuplicateClassCode(generateClassesString: String): String {
        return if (ConfigManager.isInnerClassModel.not()) {
            val set = mutableSetOf<String>()
            set.addAll(generateClassesString.split("\n\n"))
            set.joinToString("\n\n")
        } else {
            generateClassesString
        }
    }
}
