package wu.seal.jsontokotlin.model.builder

import wu.seal.jsontokotlin.model.classscodestruct.Annotation
import wu.seal.jsontokotlin.model.classscodestruct.KotlinClass
import wu.seal.jsontokotlin.model.classscodestruct.Property
import wu.seal.jsontokotlin.model.classscodestruct.getAllGenericsRecursively

/**
 * Created by Nstd on 2020/6/29 15:35.
 */
abstract class BaseCodeBuilder(
        val name: String,
        val annotations: List<Annotation> = listOf(),
        val properties: List<Property> = listOf(),
        val parentClassTemplate: String = "",
        val modifiable: Boolean = true,
        val comments: String = "",
        val fromJsonSchema: Boolean = false
        ): ICodeBuilder {

    val referencedClasses: List<KotlinClass>
        get() {
            return properties.flatMap { property ->
                mutableListOf(property.typeObject).apply {
                    addAll(property.typeObject.getAllGenericsRecursively())
                }
            }
        }

}