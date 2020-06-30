package wu.seal.jsontokotlin.model.builder

import wu.seal.jsontokotlin.model.classscodestruct.DataClass
import wu.seal.jsontokotlin.model.classscodestruct.KotlinClass
import wu.seal.jsontokotlin.model.classscodestruct.getAllGenericsRecursively

/**
 * java class code generator
 *
 * Created by Nstd on 2020/6/30 14:57.
 */
class JavaCodeBuilder(clazz: DataClass)
        : BaseClassCodeBuilder(
                clazz.name,
                clazz.modifiable,
                clazz.annotations,
                clazz.properties,
                clazz.parentClassTemplate,
                clazz.comments,
                clazz.fromJsonSchema
                ) {

    val referencedClasses: List<KotlinClass>
        get() {
            return properties.flatMap { property ->
                mutableListOf(property.typeObject).apply {
                    addAll(property.typeObject.getAllGenericsRecursively())
                }
            }
        }

    override fun getCode(): String {
        //TODO to be implemented
        return ""
    }

    override fun getOnlyCurrentCode(): String {
        //TODO to be implemented
        return ""
    }
}