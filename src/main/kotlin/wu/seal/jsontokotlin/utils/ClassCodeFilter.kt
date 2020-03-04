package wu.seal.jsontokotlin.utils

import wu.seal.jsontokotlin.model.ConfigManager

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
            generateClassesString.split("\n\n").distinct().joinToString("\n\n")
        } else {
            generateClassesString
        }
    }
}
